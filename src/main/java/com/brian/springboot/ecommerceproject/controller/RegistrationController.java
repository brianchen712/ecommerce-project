package com.brian.springboot.ecommerceproject.controller;

import java.util.Optional;
import java.util.logging.Logger;

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
import com.brian.springboot.ecommerceproject.service.UserService;

@Controller
@RequestMapping("/register")
public class RegistrationController {

	private UserService userService;

	public RegistrationController(UserService userService) {
		this.userService = userService;
	}

	private Logger logger = Logger.getLogger(getClass().getName());

	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {

		// 若頁面欄位內容有空白，則將空白去除
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}

	@GetMapping("/showRegistrationForm")
	public String showMyLoginPage(Model theModel) {
		// 將crmUser物件與前端表單資料綁定
		theModel.addAttribute("crmUser", new CrmUser());

		return "registration-form";
	}

	@PostMapping("/processRegistrationForm")
	public String processRegistrationForm(@Valid @ModelAttribute("crmUser") CrmUser theCrmUser,
			BindingResult theBindingResult, Model theModel) {

		String userName = theCrmUser.getUsername();
		String email = theCrmUser.getEmail();
		logger.info("Processing registration form for userName: " + userName + "email: " + email);

		// 檢查使用者跟Email是否存在
		Optional<User> userByName = userService.findUserByName(userName);
		Optional<User> userByemail = userService.findUserByEmail(email);

		// 欄位檢核
		if (userByName.isPresent() || userByemail.isPresent()
				|| theBindingResult.hasErrors()) {
			if (userByName.isPresent()) {
				theModel.addAttribute("userError", "使用者已存在");
				logger.warning("使用者已存在");
			}
			if (userByemail.isPresent()) {
				theModel.addAttribute("emailError", "Email已被使用");
				logger.warning("Email已被使用");
			}

			return "registration-form";
		}

		// 儲存user
		userService.saveUser(theCrmUser);

		logger.info("Successfully created user: " + userName);

		return "registration-success";
	}
}
