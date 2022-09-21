package com.brian.springboot.ecommerceproject.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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

import com.brian.springboot.ecommerceproject.entity.Course;
import com.brian.springboot.ecommerceproject.entity.Review;
import com.brian.springboot.ecommerceproject.entity.User;
import com.brian.springboot.ecommerceproject.form.ReviewForm;
import com.brian.springboot.ecommerceproject.model.AllReviewForFronted;
import com.brian.springboot.ecommerceproject.model.CourseAndInstructorForFronted;
import com.brian.springboot.ecommerceproject.model.ReviewInfo;
import com.brian.springboot.ecommerceproject.service.CommonService;
import com.brian.springboot.ecommerceproject.service.CourseContentService;
import com.brian.springboot.ecommerceproject.utils.ControllerUtils;

@Controller
@RequestMapping("/ecommerce")
public class CourseContentController {

	private CourseContentService courseContentService;

	private CommonService commonService;

	public CourseContentController(CourseContentService courseContentService, CommonService commonService) {
		this.courseContentService = courseContentService;
		this.commonService = commonService;
	}

	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {

		// 若頁面欄位內容有空白，則將空白去除
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}

	// 顯示課程內容頁內容
	@GetMapping("/showCourseContent")
	public String showCourseContent(@RequestParam("courseId") int courseId, Model model, HttpServletRequest request,HttpSession session) throws Exception {
		
		Optional<Course> optCourse = commonService.findCourseById(courseId);
		CourseAndInstructorForFronted courseAndInstructor = ControllerUtils.geneCourseAndInstructor(optCourse);
		
		model.addAttribute("courseTypes", (List)session.getAttribute("courseTypes"));
		model.addAttribute("instructor", Optional.ofNullable(courseAndInstructor.getInstructor()).orElse(null));
		model.addAttribute("course", Optional.ofNullable(courseAndInstructor.getCourse()).orElse(null));
		
		model.addAttribute("cartCourseIdMap", (Map)session.getAttribute("cartCourseIdMap"));
		model.addAttribute("cartCourseSize", (int)session.getAttribute("cartCourseSize"));
		
		model.addAttribute("userName",(Optional.ofNullable(((User)session.getAttribute("user")).getUsername()).isPresent() ? Optional.ofNullable(((User)session.getAttribute("user")).getUsername()).get().substring(0, 1) : ""));
		model.addAttribute("reviewForm", new ReviewForm());
		
		User user = (User)session.getAttribute("user");
		ReviewInfo reviewInfo = courseContentService.getReviews(user, optCourse);
		AllReviewForFronted allReview = ControllerUtils.geneAllReviewForFronted(reviewInfo);
		
		model.addAttribute("reviewInfo", allReview);	
		
		return "ecommerce/course-content";
	}

	@PostMapping("/saveReview")
	public String saveReview(@Valid @ModelAttribute("reviewForm") ReviewForm reviewForm, BindingResult theBindingResult,
			HttpSession session, Model model, @RequestParam("courseId") int courseId) throws Exception {
		Optional<Course> optCourse = commonService.findCourseById(courseId);
		CourseAndInstructorForFronted courseAndInstructor = ControllerUtils.geneCourseAndInstructor(optCourse);

		model.addAttribute("courseTypes", (List) session.getAttribute("courseTypes"));
		model.addAttribute("instructor", courseAndInstructor.getInstructor());
		model.addAttribute("course", courseAndInstructor.getCourse());

		model.addAttribute("cartCourseIdMap", (Map) session.getAttribute("cartCourseIdMap"));
		model.addAttribute("cartCourseSize", (int) session.getAttribute("cartCourseSize"));
		
		model.addAttribute("userName", ((User) session.getAttribute("user")).getUsername().substring(0, 1));
		
		User user = (User)session.getAttribute("user");
		ReviewInfo reviewInfo = courseContentService.getReviews(user, optCourse);
		AllReviewForFronted allReview = ControllerUtils.geneAllReviewForFronted(reviewInfo);
		
		model.addAttribute("reviewInfo", allReview);	

		// 欄位檢核
		if (theBindingResult.hasErrors()) {
			return "ecommerce/course-content";
		}

		Review review = ControllerUtils.getReview(user, optCourse, reviewForm);

		courseContentService.saveReview(review);

		return "redirect:/ecommerce/showCourseContent?courseId=" + courseId;
	}

}
