package com.brian.springboot.ecommerceproject.controller;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.brian.springboot.ecommerceproject.entity.Course;
import com.brian.springboot.ecommerceproject.service.MyCourseService;
import com.brian.springboot.ecommerceproject.utils.ControllerUtils;

@Controller
@RequestMapping("/ecommerce")
public class MyCourseController {
	
	private MyCourseService myCourseService;
	
	public MyCourseController(MyCourseService myCourseService) {
		this.myCourseService = myCourseService;
	}
	
	// 顯示首頁內容
	@GetMapping("/showMyCourse")
	public String showMyCoursePage(Model model, HttpSession session) {
		
		Map<Integer,Integer> orderCourseIdMap = (Map)session.getAttribute("orderCourseIdMap");
		
		List<Integer> orderCourseIds = orderCourseIdMap.keySet().stream().collect(Collectors.toList());
		//透過訂單上的課程編號取得課程資料
		List<Course> orderCourses = myCourseService.getCourses(orderCourseIds);
		
		// 將選單上的課程類別放入model
		model.addAttribute("courseTypes", (List)session.getAttribute("courseTypes"));
		// 將我的課程放入model
		model.addAttribute("myCourses", ControllerUtils.geneCourses(orderCourses));
		// 將購物車課程數量放入model
		model.addAttribute("cartCourseSize", (int)session.getAttribute("cartCourseSize"));
		return "ecommerce/my-course";
	}
}
