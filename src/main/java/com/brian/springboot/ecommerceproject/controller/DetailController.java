package com.brian.springboot.ecommerceproject.controller;

import java.util.ArrayList;
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

import com.brian.springboot.ecommerceproject.entity.Course;
import com.brian.springboot.ecommerceproject.entity.Instructor;
import com.brian.springboot.ecommerceproject.model.CourseAndInstructorForFronted;
import com.brian.springboot.ecommerceproject.model.CourseForFronted;
import com.brian.springboot.ecommerceproject.service.CommonService;
import com.brian.springboot.ecommerceproject.utils.ControllerUtils;

@Controller
@RequestMapping("/ecommerce")
public class DetailController {

	private CommonService commonService;

	private Logger logger = Logger.getLogger(getClass().getName());

	public DetailController(CommonService commonService) {
		this.commonService = commonService;
	}

	// 顯示明細頁內容
	@GetMapping("/showDetail")
	public String showDetailPage(Model model, HttpServletRequest request, HttpSession session) throws Exception {
		logger.info("in showDetail");
		int tempCourseId = -1;
		if (Optional.ofNullable(request.getParameter("courseId")).isPresent()) {
			// 從首頁進到明細頁，courseId會存入request
			tempCourseId = Integer.parseInt(Optional.ofNullable(request.getParameter("courseId")).get());
		} else {
			// 點選加入購物車，重新回到明細頁，courseId會存入session
			tempCourseId = (int) session.getAttribute("detailCourseId");
		}

		final int courseId = tempCourseId;

		logger.info("courseId : " + courseId);

		Optional<Course> optCourse = commonService.findCourseById(courseId);
		
		List<Course> courses = optCourse.map(Course::getInstructor).map(Instructor::getCourses).orElse(new ArrayList<>());
		// 取得課程講師其他課程內容
		List<Course> relatedCourses = courses.stream().filter(c -> c.getId() != courseId).collect(Collectors.toList());
		// 組出明細頁會顯示的課程+講師內容
		CourseAndInstructorForFronted courseAndInstructor = ControllerUtils.geneCourseAndInstructor(optCourse);
		// 組出明細頁會顯示的講師相關課程內容
		List<CourseForFronted> relatedCoursesFronted = ControllerUtils.geneCourses(relatedCourses);

		// 將選單上的課程類別放入model
		model.addAttribute("courseTypes", (List) session.getAttribute("courseTypes"));
		// 將課程相關內容放入model
		model.addAttribute("instructor", courseAndInstructor.getInstructor());
		model.addAttribute("course", courseAndInstructor.getCourse());
		model.addAttribute("relatedCourses", relatedCoursesFronted);
		// 將購物車相關內容放入model
		model.addAttribute("cartCourseIdMap", (Map) session.getAttribute("cartCourseIdMap"));
		model.addAttribute("cartCourseSize", (int) session.getAttribute("cartCourseSize"));
		model.addAttribute("orderCourseIdMap", (Map) session.getAttribute("orderCourseIdMap"));

		return "ecommerce/detail";
	}

}
