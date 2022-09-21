package com.brian.springboot.ecommerceproject.service;

import java.sql.Timestamp;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brian.springboot.ecommerceproject.dao.UserRepository;
import com.brian.springboot.ecommerceproject.entity.User;
import com.brian.springboot.ecommerceproject.form.CrmUser;

@Service
public class MyProfileServiceImpl implements MyProfileService {
	
	private UserRepository userRepository;
	
	public MyProfileServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	@Transactional
	public void saveMyProfile(CrmUser crmUser, User user) {
		user.setUsername(crmUser.getUsername());
		user.setPassword(new BCryptPasswordEncoder().encode(crmUser.getPassword()));
		user.setFullName(crmUser.getFullName());
		user.setEmail(crmUser.getEmail());
		user.setCreateDate(new Timestamp(System.currentTimeMillis()));
		user.setUpdateDate(new Timestamp(System.currentTimeMillis()));
		
		// 儲存使用者資訊
		userRepository.save(user);
		
	}
}
