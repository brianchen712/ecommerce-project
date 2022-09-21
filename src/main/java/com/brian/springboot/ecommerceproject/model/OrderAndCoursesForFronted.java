package com.brian.springboot.ecommerceproject.model;

public class OrderAndCoursesForFronted {
	private String image;
	
	private String courseName;
	
	private String coursePrice;

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getCoursePrice() {
		return coursePrice;
	}

	public void setCoursePrice(String coursePrice) {
		this.coursePrice = coursePrice;
	}
}
