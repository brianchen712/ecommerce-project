package com.brian.springboot.ecommerceproject.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.brian.springboot.ecommerceproject.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	 @Query(value = "select * from user where username = ?1", nativeQuery = true)
	 Optional<User> findByUserName(String username);
	 
	 @Query(value = "select * from user where email = ?1", nativeQuery = true)
	 Optional<User> findByUserEmail(String email);
}
