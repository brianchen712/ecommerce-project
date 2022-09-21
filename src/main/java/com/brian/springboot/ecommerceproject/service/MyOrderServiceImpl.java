package com.brian.springboot.ecommerceproject.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brian.springboot.ecommerceproject.dao.OrderRepository;
import com.brian.springboot.ecommerceproject.entity.Course;
import com.brian.springboot.ecommerceproject.entity.Order;
import com.brian.springboot.ecommerceproject.model.OrderAndCoursesInfo;

@Service
public class MyOrderServiceImpl implements MyOrderService {

	private OrderRepository orderRepository;
	
	MyOrderServiceImpl(OrderRepository orderRepository){
		this.orderRepository = orderRepository;
	}
	
	@Override
	@Transactional
	public OrderAndCoursesInfo getOrderAndCourses(int orderId) {
		Optional<Order> optOrder = orderRepository.findById(orderId);
		
		Order order = null;
		List<Course> courses = null;
		if(optOrder.isPresent()) {
			order = optOrder.get();
			courses = order.getCourses();
		} else {
			order = new Order();
			courses = new ArrayList<>();
		}
		
		return new OrderAndCoursesInfo(order, courses);
	}

}
