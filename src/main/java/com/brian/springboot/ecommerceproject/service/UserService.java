package com.brian.springboot.ecommerceproject.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.brian.springboot.ecommerceproject.entity.User;
import com.brian.springboot.ecommerceproject.form.CrmUser;

public interface UserService extends UserDetailsService {

	public Optional<User> findUserByName(String userName);
	
	public Optional<User> findUserByEmail(String email);
	
	public void saveUser(CrmUser crmUser);
}
