package com.brian.springboot.ecommerceproject.controller;

import java.util.List;

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

import com.brian.springboot.ecommerceproject.entity.User;
import com.brian.springboot.ecommerceproject.form.CrmUser;
import com.brian.springboot.ecommerceproject.service.MyProfileService;

@Controller
@RequestMapping("/ecommerce")
public class MyProfileController {
	
	private MyProfileService myProfileService;
	
	public MyProfileController(MyProfileService myProfileService) {
		this.myProfileService = myProfileService;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		
		// 若頁面欄位內容有空白，則將空白去除
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}	

	@GetMapping("/showMyProfile")
	public String showMyProfile(Model model, HttpSession session) {
		model.addAttribute("courseTypes", (List) session.getAttribute("courseTypes"));
		model.addAttribute("cartCourseSize", (int) session.getAttribute("cartCourseSize"));
		User user = (User) session.getAttribute("user");
		model.addAttribute("crmUser", new CrmUser(user.getUsername(), user.getPassword(),user.getPassword(), user.getFullName(), user.getEmail()));
		return "ecommerce/my-profile";
	}

	@PostMapping("/saveMyProfile")
	public String saveMyProfile(@Valid @ModelAttribute("crmUser") CrmUser crmUser, BindingResult theBindingResult,
			HttpSession session, Model model) {
		
		model.addAttribute("courseTypes", (List) session.getAttribute("courseTypes"));
		model.addAttribute("cartCourseSize", (int) session.getAttribute("cartCourseSize"));

		// 欄位檢核
		if (theBindingResult.hasErrors()) {
			return "ecommerce/my-profile";
		}
		
		User user = (User) session.getAttribute("user");
		myProfileService.saveMyProfile(crmUser, user);
		
		session.setAttribute("user", user);
		
		//修改成功，在畫面顯示修改成功的訊息
		model.addAttribute("updateSuccessInfo", "該筆資料修改成功");
		
		return "ecommerce/my-profile";
	}
	
}
