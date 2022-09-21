package com.brian.springboot.ecommerceproject.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brian.springboot.ecommerceproject.dao.RoleRepository;
import com.brian.springboot.ecommerceproject.dao.UserRepository;
import com.brian.springboot.ecommerceproject.entity.Role;
import com.brian.springboot.ecommerceproject.entity.User;
import com.brian.springboot.ecommerceproject.form.CrmUser;

@Service
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;

	private RoleRepository roleRepository;

	public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
	}

	@Override
	@Transactional
	public Optional<User> findUserByName(String userName) {
		if(Optional.ofNullable(userName).isPresent()) {
			return userRepository.findByUserName(userName);
		};
		return Optional.empty();
	}

	@Override
	@Transactional
	public Optional<User> findUserByEmail(String email) {
		if (Optional.ofNullable(email).isPresent()) {
			return userRepository.findByUserEmail(email);
		}
		return Optional.empty();
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		Optional<User> user = userRepository.findByUserName(userName);
		if (!user.isPresent()) {
			throw new UsernameNotFoundException("使用者不存在");
		}
		return new org.springframework.security.core.userdetails.User(user.get().getUsername(), user.get().getPassword(),
				mapRolesToAuthorities(user.get().getRoles()));
	}

	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public void saveUser(CrmUser crmUser) {
		User user = new User();
		user.setUsername(crmUser.getUsername());
		user.setPassword(new BCryptPasswordEncoder().encode(crmUser.getPassword()));
		user.setFullName(crmUser.getFullName());
		user.setEmail(crmUser.getEmail());
		user.setCreateDate(new Timestamp(System.currentTimeMillis()));
		user.setUpdateDate(new Timestamp(System.currentTimeMillis()));

		// 設定角色資訊
		Optional<Role> optRole = roleRepository.findRoleByName("ROLE_CARD MEMBER");
		if(optRole.isPresent()) {
			user.setRoles(Arrays.asList(optRole.get()));
		} else {
			user.setRoles(new ArrayList<>());
		}

		// 儲存使用者資訊
		userRepository.save(user);
	}

}
