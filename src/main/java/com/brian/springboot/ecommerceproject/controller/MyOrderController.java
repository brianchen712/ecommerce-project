package com.brian.springboot.ecommerceproject.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.brian.springboot.ecommerceproject.model.OrderAndCoursesInfo;
import com.brian.springboot.ecommerceproject.service.CommonService;
import com.brian.springboot.ecommerceproject.service.MyOrderService;
import com.brian.springboot.ecommerceproject.utils.ControllerUtils;

@Controller
@RequestMapping("/ecommerce")
public class MyOrderController {
	
	private MyOrderService myOrderService;

	public MyOrderController(MyOrderService myOrderService) {
		this.myOrderService = myOrderService;
	}

	/**
	 * 顯示我的訂單
	 * @param model
	 * @param session
	 * @return
	 */
	@GetMapping("/showMyOrder")
	public String showMyOrder(Model model, HttpSession session) {
		// 將選單上的課程類別放入model
		model.addAttribute("courseTypes", (List) session.getAttribute("courseTypes"));
		// 將購物車課程數量放入model
		model.addAttribute("cartCourseSize", (int) session.getAttribute("cartCourseSize"));
		
		// 將訂單內容放入model
		model.addAttribute("orders", ControllerUtils.geneOrders((List)session.getAttribute("orders")));
		model.addAttribute("orderSize", session.getAttribute("orderSize"));

		return "ecommerce/my-order";
	}
	
	/**
	 * 顯示我的訂單明細
	 * @param orderId
	 * @param model
	 * @param session
	 * @return
	 */
	@GetMapping("/showMyOrderDetail")
	public String showMyOrderDetail(@RequestParam("orderId") int orderId, Model model, HttpSession session) {
		// 將選單上的課程類別放入model
		model.addAttribute("courseTypes", (List) session.getAttribute("courseTypes"));
		// 將購物車課程數量放入model
		model.addAttribute("cartCourseSize", (int) session.getAttribute("cartCourseSize"));
		
		OrderAndCoursesInfo orderAndCourses = myOrderService.getOrderAndCourses(orderId);
		
		// 將訂單明細內容放入model
		model.addAttribute("orderDetails", ControllerUtils.geneOrderAndCoursesForFronteds(orderAndCourses));
		model.addAttribute("orderCourseSize", orderAndCourses.getCourses().size());

		return "ecommerce/my-order-detail";
	}
}
