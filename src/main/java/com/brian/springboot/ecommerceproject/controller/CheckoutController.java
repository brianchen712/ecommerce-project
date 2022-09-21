package com.brian.springboot.ecommerceproject.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

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

import com.brian.springboot.ecommerceproject.entity.Course;
import com.brian.springboot.ecommerceproject.entity.CreditCard;
import com.brian.springboot.ecommerceproject.entity.Order;
import com.brian.springboot.ecommerceproject.entity.User;
import com.brian.springboot.ecommerceproject.form.CheckoutForm;
import com.brian.springboot.ecommerceproject.model.CreditCardInfo;
import com.brian.springboot.ecommerceproject.model.OrdersAndCoursesForFronted;
import com.brian.springboot.ecommerceproject.service.CheckoutService;
import com.brian.springboot.ecommerceproject.service.CommonService;
import com.brian.springboot.ecommerceproject.utils.ControllerUtils;

@Controller
@RequestMapping("/ecommerce")
public class CheckoutController {

	private CheckoutService checkoutService;

	private CommonService commonService;

	private Logger logger = Logger.getLogger(getClass().getName());

	public CheckoutController(CheckoutService checkoutService, CommonService commonService) {
		this.checkoutService = checkoutService;
		this.commonService = commonService;
	}

	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {

		// 若頁面欄位內容有空白，則將空白去除
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}

	/**
	 * 顯示結帳頁
	 * 
	 * @param model
	 * @param session
	 * @return
	 */
	@GetMapping("/showCheckout")
	public String showCheckout(Model model, HttpSession session) {
		logger.info("in showCheckout: ");
		// 將選單上課程種類放入model
		model.addAttribute("courseTypes", (List) session.getAttribute("courseTypes"));
		// 將購物車資訊放入model
		model.addAttribute("cartCoursesPrice", session.getAttribute("cartCoursesPrice"));
		model.addAttribute("cartCoursesDiscountPrice", session.getAttribute("cartCoursesDiscountPrice"));
		model.addAttribute("cartDiscount", session.getAttribute("cartDiscount"));

		// 取得信用卡資訊
		User user = (User) session.getAttribute("user");
		CreditCardInfo creditCard = commonService.getCreditCardInfo(user.getId());
		CreditCard defaultCard = creditCard.getDefaultCard();

		// 有預設信用卡。則將信用卡資訊帶到畫面上
		if (Optional.ofNullable(defaultCard).isPresent()) {
			if (Optional.ofNullable(defaultCard.getId()).isPresent()
					&& Optional.ofNullable(defaultCard.getCreditCardNo()).isPresent()
					&& Optional.ofNullable(defaultCard.getExpiredDate()).isPresent()
					&& Optional.ofNullable(defaultCard.getCvv()).isPresent()) {

				String expiredDate = Optional.ofNullable(defaultCard.getExpiredDate()).get();
				String frontedExpiredDate = new StringBuffer().append(expiredDate.substring(0,2)).append("/").append(expiredDate.substring(2,4)).toString();
				model.addAttribute("checkoutForm",
						new CheckoutForm(Optional.ofNullable(defaultCard.getId()).get(),
								Optional.ofNullable(defaultCard.getCreditCardNo()).get(),
								frontedExpiredDate,
								Optional.ofNullable(defaultCard.getCvv()).get()));

				logger.info("defaultCard's no : " + defaultCard.getCreditCardNo() + " is set on the model");
			}
		} else {
			model.addAttribute("checkoutForm", new CheckoutForm());
		}

		return "ecommerce/checkout";
	}

	/**
	 * 點選輸入新卡片的處理
	 * 
	 * @param model
	 * @param session
	 * @return
	 */
	@GetMapping("/showCheckoutV2")
	public String showCheckoutV2(Model model, HttpSession session) {
		// 課程種類放入model中
		model.addAttribute("courseTypes", (List) session.getAttribute("courseTypes"));
		// 購物車資訊放入model中
		model.addAttribute("cartCoursesPrice", session.getAttribute("cartCoursesPrice"));
		model.addAttribute("cartCoursesDiscountPrice", session.getAttribute("cartCoursesDiscountPrice"));
		model.addAttribute("cartDiscount", session.getAttribute("cartDiscount"));
		// 結帳表單放入model中
		model.addAttribute("checkoutForm", new CheckoutForm());
		return "ecommerce/checkout";
	}

	/**
	 * 結帳處理
	 * 
	 * @param checkoutForm
	 * @param theBindingResult
	 * @param session
	 * @param model
	 * @return
	 */
	@PostMapping("/checkout")
	public String checkout(@Valid @ModelAttribute("checkoutForm") CheckoutForm checkoutForm,
			BindingResult theBindingResult, HttpSession session, Model model) {
		logger.info("in checkout: ");
		// 課程種類放入model中
		model.addAttribute("courseTypes", (List) session.getAttribute("courseTypes"));
		// 購物車資訊放入model中
		model.addAttribute("cartCoursesPrice", session.getAttribute("cartCoursesPrice"));
		model.addAttribute("cartCoursesDiscountPrice", session.getAttribute("cartCoursesDiscountPrice"));
		model.addAttribute("cartDiscount", session.getAttribute("cartDiscount"));

		// 檢查信用卡號是否存在
		Optional<CreditCard> optCard = commonService.findCreditCard(checkoutForm.getCardNo());
		
		// 欄位檢核，且新增的卡片需判斷輸入卡號是否存在
		if (theBindingResult.hasErrors() || (optCard.isPresent() && checkoutForm.getId() == 0)) {
			if (optCard.isPresent() && checkoutForm.getId() == 0) {
				model.addAttribute("cardError", "信用卡號已存在");
				logger.warning("信用卡號已存在");
			}
			if (Optional.ofNullable(checkoutForm.getExpiredDate()).isPresent() && checkoutForm.getExpiredDate().length() == 4) {
				checkoutForm.setExpiredDate(new StringBuffer().append(checkoutForm.getExpiredDate().substring(0, 2)).append("/").append(
						checkoutForm.getExpiredDate().substring(2, 4)).toString());
			}
			return "ecommerce/checkout";
		}

		// 儲存信用卡資訊、訂單內容，購物車課程FLAG設為true
		User user = (User) session.getAttribute("user");
		int cartId = (int) session.getAttribute("cartId");
		List<Course> cartCourses = (List) session.getAttribute("cartCourses");
		String cartCourseDiscountPrice = (String) session.getAttribute("cartCoursesDiscountPrice");

		CreditCardInfo creditCard = commonService.getCreditCardInfo(user.getId());
		CreditCard addCreditCard = ControllerUtils.getCreditCard(checkoutForm, creditCard.getCreditCards(), user);

		Order order = ControllerUtils.getOrder(user, cartCourses, cartCourseDiscountPrice, addCreditCard);

		logger.info("order id: " + order.getId());

		checkoutService.saveCheckout(checkoutForm, addCreditCard, user, cartId, order);
		logger.info("Successfully created addCreditCard's no" + addCreditCard.getCreditCardNo());

		// 清空session中的購物車資訊
		clearCartSeesion(session);

		// 取得給畫面使用的訂單內容
		List<Order> orders = commonService.getOrders(user.getId());
		OrdersAndCoursesForFronted ordersAndCoursesForFronted = ControllerUtils.getOrdersAndCoursesForFronted(orders);

		// 在session存放訂單資訊
		setOrderSession(session, ordersAndCoursesForFronted);

		return "ecommerce/checkout-success";
	}

	/**
	 * 清空session中的購物車資訊
	 * 
	 * @param session
	 */
	private void clearCartSeesion(HttpSession session) {
		session.setAttribute("cartId", 0);
		session.setAttribute("cartCourseIdMap", new HashMap<>());
		session.setAttribute("cartCourseSize", 0);
	}

	/**
	 * // 在session存放訂單資訊
	 * 
	 * @param session
	 * @param ordersAndCoursesForFronted
	 */
	private void setOrderSession(HttpSession session, OrdersAndCoursesForFronted ordersAndCoursesForFronted) {
		// 有資料:就直接取得訂單的資料
		if (ordersAndCoursesForFronted.getOrderSize() > 0) {
			session.setAttribute("orders", ordersAndCoursesForFronted.getOrders());
			session.setAttribute("orderSize", ordersAndCoursesForFronted.getOrderSize());
			session.setAttribute("orderCourseIdMap", ordersAndCoursesForFronted.getOrdersCourseIdMap());
			session.setAttribute("orderCourseSize", ordersAndCoursesForFronted.getOrdersCourseIdMap().keySet().size());
		} else {
			session.setAttribute("orders", new ArrayList<>());
			session.setAttribute("orderSize", 0);
			session.setAttribute("orderCourseIdMap", new HashMap<>());
			session.setAttribute("orderCourseSize", 0);
		}
	}
}
