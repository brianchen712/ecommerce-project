package com.brian.springboot.ecommerceproject.model;

import java.util.List;

import com.brian.springboot.ecommerceproject.entity.Course;
import com.brian.springboot.ecommerceproject.entity.OrdersCourses;
import com.brian.springboot.ecommerceproject.entity.Review;

public class CourseInfo {
	private List<Course> courses;
	
	private List<Review> reviews;
	
	private List<OrdersCourses> ordersCourses;

	public CourseInfo() {
	}

	public CourseInfo(List<Course> courses, List<Review> reviews, List<OrdersCourses> ordersCourses) {
		this.courses = courses;
		this.reviews = reviews;
		this.ordersCourses = ordersCourses;
	}

	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	public List<OrdersCourses> getOrdersCourses() {
		return ordersCourses;
	}

	public void setOrdersCourses(List<OrdersCourses> ordersCourses) {
		this.ordersCourses = ordersCourses;
	}
	
}
