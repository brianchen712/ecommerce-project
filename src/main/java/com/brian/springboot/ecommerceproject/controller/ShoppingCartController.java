package com.brian.springboot.ecommerceproject.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.brian.springboot.ecommerceproject.entity.Course;
import com.brian.springboot.ecommerceproject.service.ShoppingCartService;
import com.brian.springboot.ecommerceproject.utils.ControllerUtils;

@Controller
@RequestMapping("/ecommerce")
public class ShoppingCartController {

	private ShoppingCartService shoppingCartService;

	private Logger logger = Logger.getLogger(getClass().getName());

	public ShoppingCartController(ShoppingCartService shoppingCartService) {
		this.shoppingCartService = shoppingCartService;
	}

	@GetMapping("/showShoppingCart")
	public String showShoppingCart(Model model, HttpSession session) {
		// 透過購物車的課程編號清單，取得課程相關訊息
		@SuppressWarnings("unchecked")
		Map<Integer, Integer> cartCourseIdMap = (Map) session.getAttribute("cartCourseIdMap");
		List<Integer> cartCourseIds = cartCourseIdMap.keySet().stream().collect(Collectors.toList());

		List<Course> courses = shoppingCartService.getCourseByIds(cartCourseIds);
		// 選單上的課程種類放入model
		model.addAttribute("courseTypes", (List) session.getAttribute("courseTypes"));

		// 課程數量及內容放入model
		model.addAttribute("cartCourses", ControllerUtils.geneCourses(courses));
		model.addAttribute("cartCoursesSize", courses.size());

		// 課程金額放入model
		String[] coursePrice = ControllerUtils.genePrices(courses);
		model.addAttribute("cartCoursesPrice", coursePrice[0]);
		String[] courseDiscountPrice = ControllerUtils.geneDiscountPrices(courses);
		model.addAttribute("cartCoursesDiscountPrice", courseDiscountPrice[0]);
		model.addAttribute("cartDiscount", ControllerUtils.geneDiscount(coursePrice[1], courseDiscountPrice[1]));

		// 課程內容跟課程總額放入session
		session.setAttribute("cartCourses", courses);
		session.setAttribute("cartCoursesPrice", coursePrice[0]);
		session.setAttribute("cartCoursesDiscountPrice", courseDiscountPrice[0]);
		session.setAttribute("cartDiscount", ControllerUtils.geneDiscount(coursePrice[1], courseDiscountPrice[1]));

		return "ecommerce/shopping-cart";
	}

	@GetMapping("/addShoppingCart")
	public String addShoppingCart(@RequestParam("pageName") String pageName, @RequestParam("courseId") int courseId,
			HttpSession session, HttpServletRequest request, Model model) throws Exception {

		logger.info("in addShoppingCart");
		ControllerUtils.processAddCart(shoppingCartService, courseId, session, request);

		// 點選相關課程的加入購物車，需要將畫面上的主要課程編號傳回明細頁
		if (Optional.ofNullable(request.getParameter("primaryCourseId")).isPresent()) {
			courseId = Integer.parseInt(request.getParameter("primaryCourseId"));
		}

		logger.info("courseId: " + courseId);

		session.setAttribute("detailCourseId", courseId);

		return new StringBuffer().append("redirect:/ecommerce/show").append(pageName).toString();
	}

	/**
	 * 點選立即購買的處理
	 * @param courseId
	 * @param session
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/addAndShowShoppingCart")
	public String addAndShowShoppingCart(@RequestParam("courseId") int courseId, HttpSession session,
			HttpServletRequest request, Model model) throws Exception {

		ControllerUtils.processAddCart(shoppingCartService, courseId, session, request);

		return "redirect:/ecommerce/showShoppingCart";
	}

	@GetMapping("/removeShoppingCart")
	public String removeShoppingCart(@RequestParam("courseId") int courseId, Model model, HttpSession session) {
		logger.info("in removeShoppingCart");

		int cartId = (Integer) session.getAttribute("cartId");
		Map<Integer, Integer> cartCourseIdMap = shoppingCartService.removeShoppingCart(cartId, courseId);
		logger.info("cartId: " + cartId + ", courseId:" + courseId);

		if (Optional.ofNullable(cartCourseIdMap).isPresent()) {
			session.setAttribute("cartCourseIdMap", cartCourseIdMap);
			session.setAttribute("cartCourseSize", cartCourseIdMap.keySet().size());
		} else {
			session.setAttribute("cartId", 0);
			session.setAttribute("cartCourseIdMap", new HashMap<>());
			session.setAttribute("cartCourseSize", 0);
		}
		return "redirect:/ecommerce/showShoppingCart";
	}
}
