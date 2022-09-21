package com.brian.springboot.ecommerceproject.model;

import java.util.List;

import com.brian.springboot.ecommerceproject.entity.Course;

public class CartAndCourseInfo {
	private int cartId;
	private List<Course> courses;
	
	public CartAndCourseInfo() {
	}
	
	public CartAndCourseInfo(int cartId, List<Course> courses) {
		this.cartId = cartId;
		this.courses = courses;
	}
	
	
	public int getCartId() {
		return cartId;
	}

	public void setCartId(int cartId) {
		this.cartId = cartId;
	}

	public List<Course> getCourses() {
		return courses;
	}
	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}
	
	
}
