package com.brian.springboot.ecommerceproject.service;

import com.brian.springboot.ecommerceproject.entity.User;
import com.brian.springboot.ecommerceproject.form.CrmUser;

public interface MyProfileService {
	public void saveMyProfile(CrmUser crmUser, User user);
}
