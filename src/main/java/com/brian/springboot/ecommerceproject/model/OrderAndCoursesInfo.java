package com.brian.springboot.ecommerceproject.model;

import java.util.List;

import com.brian.springboot.ecommerceproject.entity.Course;
import com.brian.springboot.ecommerceproject.entity.Order;

public class OrderAndCoursesInfo {
	
	private Order order;

	private List<Course> courses;

	public OrderAndCoursesInfo() {
	}

	public OrderAndCoursesInfo(Order order, List<Course> courses) {
		this.order = order;
		this.courses = courses;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

}
