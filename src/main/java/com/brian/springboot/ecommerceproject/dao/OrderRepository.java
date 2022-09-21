package com.brian.springboot.ecommerceproject.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.brian.springboot.ecommerceproject.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
	@Query(value = "select * from `order` where user_id = ?1", nativeQuery = true)
	List<Order> findByUserId(int userId);
}
