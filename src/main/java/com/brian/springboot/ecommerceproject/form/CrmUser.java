package com.brian.springboot.ecommerceproject.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.brian.springboot.ecommerceproject.validator.FieldMatch;
import com.brian.springboot.ecommerceproject.validator.ValidEmail;

@FieldMatch.List({
    @FieldMatch(first = "password", second = "matchingPassword", message = "密碼需一致")
})
public class CrmUser {

	@NotNull(message = "使用者名稱必填")
	@Size(min = 1, message = "使用者名稱必填")
	private String username;

	@NotNull(message = "密碼必填")
	@Size(min = 1, message = "密碼必填")
	private String password;
	
	@NotNull(message = "確認密碼必填")
	@Size(min = 1, message = "確認密碼必填")
	private String matchingPassword;

	@NotNull(message = "全名必填")
	@Size(min = 2, message = "全名必填")
	private String fullName;

	@ValidEmail
	@NotNull(message = "信箱必填")
	@Size(min = 1, message = "信箱必填")
	private String email;

	public CrmUser() {

	}

	

	public CrmUser(String username,String password,String matchingPassword,String fullName,String email) {
		this.username = username;
		this.password = password;
		this.matchingPassword = matchingPassword;
		this.fullName = fullName;
		this.email = email;
	}



	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMatchingPassword() {
		return matchingPassword;
	}

	public void setMatchingPassword(String matchingPassword) {
		this.matchingPassword = matchingPassword;
	}

	public String getFullName() {
		return fullName;
	}


	public void setFullName(String fullName) {
		this.fullName = fullName;
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
