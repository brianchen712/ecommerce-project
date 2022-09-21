package com.brian.springboot.ecommerceproject.controller;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.brian.springboot.ecommerceproject.entity.CreditCard;
import com.brian.springboot.ecommerceproject.entity.User;
import com.brian.springboot.ecommerceproject.form.CheckoutForm;
import com.brian.springboot.ecommerceproject.model.CreditCardInfo;
import com.brian.springboot.ecommerceproject.service.CommonService;
import com.brian.springboot.ecommerceproject.service.MyCreditCardService;
import com.brian.springboot.ecommerceproject.utils.ControllerUtils;

@Controller
@RequestMapping("/ecommerce")
public class MyCreditCardController {

	private MyCreditCardService myCreditCardService;

	private CommonService commonService;

	private Logger logger = Logger.getLogger(getClass().getName());;

	public MyCreditCardController(MyCreditCardService myCreditCardService, CommonService commonService) {
		this.myCreditCardService = myCreditCardService;
		this.commonService = commonService;
	}

	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {

		// 若頁面欄位內容有空白，則將空白去除
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}

	/**
	 * 顯示我的信用卡頁
	 * 
	 * @param request
	 * @param model
	 * @param session
	 * @return
	 */
	@GetMapping("/showMyCreditCard")
	public String showMyCreditCard(HttpServletRequest request, Model model, HttpSession session) {
		// 將選單上的課程類別放入model
		model.addAttribute("courseTypes", (List) session.getAttribute("courseTypes"));
		// 將購物車課程數量放入model
		model.addAttribute("cartCourseSize", (int) session.getAttribute("cartCourseSize"));

		// 取得信用卡內容及數量
		User user = (User) session.getAttribute("user");
		CreditCardInfo creditCardInfo = commonService.getCreditCardInfo(user.getId());
		// 將信用卡內容及數量放入model
		model.addAttribute("cards", ControllerUtils.geneCreditCardForFronted(creditCardInfo.getCreditCards()));
		model.addAttribute("cardSize", creditCardInfo.getCardSize());
		return "ecommerce/my-credit-card";
	}

	/**
	 * 顯示信用卡表單
	 * 
	 * @param request
	 * @param model
	 * @param session
	 * @return
	 */
	@GetMapping("/showMyCreditCardForm")
	public String showMyCreditCardForm(HttpServletRequest request, Model model, HttpSession session) {
		// 將選單上的課程類別放入model
		model.addAttribute("courseTypes", (List) session.getAttribute("courseTypes"));
		// 將購物車課程數量放入model
		model.addAttribute("cartCourseSize", (int) session.getAttribute("cartCourseSize"));

		// 有帶入卡片編號代表修改資料，沒有帶代表新增資料
		if (Optional.ofNullable(request.getParameter("cardId")).isPresent()) {
			String cardId = Optional.ofNullable(request.getParameter("cardId")).get();
			User user = (User) session.getAttribute("user");
			CreditCardInfo creditCardInfo = commonService.getCreditCardInfo(user.getId());
			CreditCard creditCard = ControllerUtils.findCredit(creditCardInfo.getCreditCards(),
					Integer.parseInt(cardId));
			model.addAttribute("checkoutForm", new CheckoutForm(creditCard.getId(), creditCard.getCreditCardNo(),
					creditCard.getExpiredDate().substring(0, 2) + "/" + creditCard.getExpiredDate().substring(2, 4),
					creditCard.getCvv()));

			logger.info("showMyCreditCardForm :" + creditCard.getId() + "is set on the model");
		} else {
			model.addAttribute("checkoutForm", new CheckoutForm());
		}
		return "ecommerce/my-credit-card-form";
	}

	/**
	 * 設定預設信用卡
	 * 
	 * @param cardId
	 * @param session
	 * @param model
	 * @return
	 */
	@GetMapping("/setDefaultCreditCard")
	public String setDefaultCreditCard(@RequestParam("cardId") int cardId, HttpSession session, Model model) {
		logger.info("in setDefaultCreditCard");
		// 將選單上的課程類別放入model
		model.addAttribute("courseTypes", (List) session.getAttribute("courseTypes"));
		// 將購物車課程數量放入model
		model.addAttribute("cartCourseSize", (int) session.getAttribute("cartCourseSize"));
		// 取得信用卡資料
		User user = (User) session.getAttribute("user");
		CreditCardInfo creditCard = commonService.getCreditCardInfo(user.getId());
		// 將信用卡資訊放入model
		model.addAttribute("cards", ControllerUtils.geneCreditCardForFronted(creditCard.getCreditCards()));
		model.addAttribute("cardSize", creditCard.getCardSize());
		model.addAttribute("defaultCard", creditCard.getDefaultCard());

		CreditCard defaultCard = creditCard.getDefaultCard();
		CreditCard curCreditCard = ControllerUtils.findCredit(creditCard.getCreditCards(), cardId);

		if (Optional.ofNullable(defaultCard).isPresent()) {
			logger.info("defaultCard's no " + defaultCard.getCreditCardNo() + ", curCreditCard's "
					+ curCreditCard.getCreditCardNo());
		}

		// 設定預設信用卡
		myCreditCardService.setDefaultCreditCard(curCreditCard, defaultCard);

		if (Optional.ofNullable(defaultCard).isPresent()) {
			logger.info("Successfully remove curCard's no" + "defaultCard's no " + defaultCard.getCreditCardNo()
					+ ", curCreditCard's " + curCreditCard.getCreditCardNo());
		}

		return "redirect:/ecommerce/showMyCreditCard";
	}

	/**
	 * 儲存信用卡資訊
	 * 
	 * @param checkoutForm
	 * @param theBindingResult
	 * @param session
	 * @param model
	 * @return
	 */
	@PostMapping("/saveCreditCard")
	public String saveCreditCard(@Valid @ModelAttribute("checkoutForm") CheckoutForm checkoutForm,
			BindingResult theBindingResult, HttpSession session, Model model) {
		logger.info("in saveCreditCard");
		// 將選單上的課程類別放入model
		model.addAttribute("courseTypes", (List) session.getAttribute("courseTypes"));
		// 將購物車課程數量放入model
		model.addAttribute("cartCourseSize", (int) session.getAttribute("cartCourseSize"));

		// 取得信用卡資訊
		User user = (User) session.getAttribute("user");
		CreditCardInfo creditCard = commonService.getCreditCardInfo(user.getId());

		// 將信用卡資訊放入model
		model.addAttribute("cards", ControllerUtils.geneCreditCardForFronted(creditCard.getCreditCards()));
		model.addAttribute("cardSize", creditCard.getCardSize());

		// 檢查信用卡號是否存在
		Optional<CreditCard> optCard = commonService.findCreditCard(checkoutForm.getCardNo());

		// 欄位檢核
		if (theBindingResult.hasErrors() || (optCard.isPresent() && checkoutForm.getId() == 0)) {
			
			if (optCard.isPresent() && checkoutForm.getId() == 0) {
				model.addAttribute("cardError", "信用卡已存在");
				logger.warning("信用卡已存在");
			}
			
			if (Optional.ofNullable(checkoutForm.getExpiredDate()).isPresent() && checkoutForm.getExpiredDate().length() == 4) {
				checkoutForm.setExpiredDate(new StringBuffer().append(checkoutForm.getExpiredDate().substring(0, 2)).append("/").append(
						checkoutForm.getExpiredDate().substring(2, 4)).toString());
			}
			return "ecommerce/my-credit-card-form";
		}

		CreditCard curCard = ControllerUtils.getCreditCard(checkoutForm, creditCard.getCreditCards(), user);
		logger.info("curCard's no" + curCard.getCreditCardNo());

		// 儲存信用卡資料
		myCreditCardService.saveCreditCard(curCard);

		logger.info("Successfully remove curCard's no" + curCard.getCreditCardNo());

		return "redirect:/ecommerce/showMyCreditCard";
	}

	@GetMapping("/removeCreditCard")
	public String removeCreditCard(@RequestParam("cardId") int cardId, HttpSession session, Model model) {
		logger.info("in removeCreditCard");
		// 將選單上的課程類別放入model
		model.addAttribute("courseTypes", (List) session.getAttribute("courseTypes"));
		// 將購物車課程數量放入model
		model.addAttribute("cartCourseSize", (int) session.getAttribute("cartCourseSize"));

		User user = (User) session.getAttribute("user");
		CreditCardInfo creditCard = commonService.getCreditCardInfo(user.getId());

		// 將信用卡資訊放入model
		model.addAttribute("cards", ControllerUtils.geneCreditCardForFronted(creditCard.getCreditCards()));
		model.addAttribute("cardSize", creditCard.getCardSize());

		CreditCard curCard = ControllerUtils.findCredit(creditCard.getCreditCards(), cardId);
		logger.info("curCard's no" + curCard.getCreditCardNo());

		// 移除信用卡
		myCreditCardService.removerCreditCard(user, creditCard.getCreditCards(), curCard);

		logger.info("Successfully remove curCard's no" + curCard.getCreditCardNo());

		return "redirect:/ecommerce/showMyCreditCard";
	}
}
