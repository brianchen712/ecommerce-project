package com.brian.springboot.ecommerceproject.service;

import com.brian.springboot.ecommerceproject.model.OrderAndCoursesInfo;

public interface MyOrderService {
	
	public OrderAndCoursesInfo getOrderAndCourses(int orderId);
	
}
