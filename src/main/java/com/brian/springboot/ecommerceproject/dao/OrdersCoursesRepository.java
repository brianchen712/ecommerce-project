package com.brian.springboot.ecommerceproject.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.brian.springboot.ecommerceproject.compositekey.OrdersCoursesKeys;
import com.brian.springboot.ecommerceproject.entity.OrdersCourses;

@Repository
public interface OrdersCoursesRepository extends JpaRepository<OrdersCourses, OrdersCoursesKeys> {
	@Query(value = "select * from orders_courses where order_id in ?1 ", nativeQuery = true)
	List<OrdersCourses> findByOrderIdIn(List<Integer> orderIds);
	
	@Query(value = "select * from orders_courses where course_id in ?1 ", nativeQuery = true)
	List<OrdersCourses> findByCourseIdIn(List<Integer> courseIds);
}
