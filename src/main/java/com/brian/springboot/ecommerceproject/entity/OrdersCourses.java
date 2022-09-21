package com.brian.springboot.ecommerceproject.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.brian.springboot.ecommerceproject.compositekey.OrdersCoursesKeys;

@Entity
@Table(name="orders_courses")
@IdClass(OrdersCoursesKeys.class)
public class OrdersCourses {
	
	@Id
	@Column(name="order_id")
	private int orderId;
	
	@Id
	@Column(name="course_id")
	private int courseId;

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
}
