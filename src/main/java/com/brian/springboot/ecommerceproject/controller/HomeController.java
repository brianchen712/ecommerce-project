package com.brian.springboot.ecommerceproject.controller;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.brian.springboot.ecommerceproject.entity.OrdersCourses;
import com.brian.springboot.ecommerceproject.entity.Review;
import com.brian.springboot.ecommerceproject.entity.User;
import com.brian.springboot.ecommerceproject.model.AllReviewForFronted;
import com.brian.springboot.ecommerceproject.model.CourseInfo;
import com.brian.springboot.ecommerceproject.service.HomeService;
import com.brian.springboot.ecommerceproject.utils.ControllerUtils;

@Controller
@RequestMapping("/ecommerce")
public class HomeController {
	
	private HomeService homeService;
	
	public HomeController(HomeService homeService) {
		this.homeService = homeService;
	}
	
	// 顯示首頁內容
	@GetMapping("/showHome")
	public String showHomePage(Model model, HttpSession session) {

		// 取得課程資訊
		User user = (User)session.getAttribute("user");
		CourseInfo coursesInfo = homeService.getCoursesInfo(user);
		
		// 將選單上的課程類別放入model
		model.addAttribute("courseTypes", (List)session.getAttribute("courseTypes"));
		// 將購物車內容放入model
		model.addAttribute("cartCourseIdMap", (Map)session.getAttribute("cartCourseIdMap"));
		model.addAttribute("cartCourseSize", (int)session.getAttribute("cartCourseSize"));
		// 將訂單上的課程id放入model
		model.addAttribute("orderCourseIdMap", (Map)session.getAttribute("orderCourseIdMap"));
		// 將課程內容放入model
		model.addAttribute("courses", ControllerUtils.geneCourses(coursesInfo.getCourses()));
		model.addAttribute("courseReviewMap", getCourseReviewMap(coursesInfo.getReviews()));
		model.addAttribute("courseStudentMap", coursesInfo.getOrdersCourses().stream().collect(Collectors.groupingBy(OrdersCourses::getCourseId, Collectors.counting())));
		
		return "ecommerce/home";
	}
	
	/**
	 * 組出課程評論資訊
	 * @param reviews
	 * @return
	 */
	private Map<Integer, AllReviewForFronted> getCourseReviewMap(List<Review> reviews) {
		Map<Integer, List<Review>> courseReviewMap = reviews.stream().collect(Collectors.groupingBy(review->review.getCourse().getId()));
		
		Map<Integer, AllReviewForFronted> result = courseReviewMap.entrySet().stream().collect(Collectors.toMap(Entry::getKey, e -> ControllerUtils.geneAllReviewForFronted(e.getValue())));
		return result;
	}
	
	// 顯示不同課程類別的首頁內容
	@GetMapping("/showHomeByType")
	public String showHomePageByType(@RequestParam("type") int typeId, Model model, HttpSession session) throws Exception {		
		
		// 取得課程資訊
		User user = (User)session.getAttribute("user");
		CourseInfo coursesInfo = homeService.getCoursesInfo(user, typeId);
		
		// 將選單上的課程類別放入model
		model.addAttribute("courseTypes", (List)session.getAttribute("courseTypes"));
		// 將購物車內容放入model
		model.addAttribute("cartCourseIdMap", (Map)session.getAttribute("cartCourseIdMap"));
		model.addAttribute("cartCourseSize", (int)session.getAttribute("cartCourseSize"));
		// 將訂單上的課程id放入model
		model.addAttribute("orderCourseIdMap", (Map)session.getAttribute("orderCourseIdMap"));
		// 將課程內容放入model
		model.addAttribute("courses", ControllerUtils.geneCourses(coursesInfo.getCourses()));
		model.addAttribute("courseReviewMap", getCourseReviewMap(coursesInfo.getReviews()));
		model.addAttribute("courseStudentMap", coursesInfo.getOrdersCourses().stream().collect(Collectors.groupingBy(OrdersCourses::getCourseId, Collectors.counting())));
		return "ecommerce/home";
	}

}
