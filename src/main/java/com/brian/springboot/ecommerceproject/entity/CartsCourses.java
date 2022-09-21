package com.brian.springboot.ecommerceproject.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.brian.springboot.ecommerceproject.compositekey.CartsCoursesKeys;

@Entity
@Table(name="carts_courses")
@IdClass(CartsCoursesKeys.class)
public class CartsCourses {
	
	@Id
	@Column(name="cart_id")
	private int cartId;
	
	@Id
	@Column(name="course_id")
	private int courseId;

	public int getCartId() {
		return cartId;
	}

	public void setCartId(int cartId) {
		this.cartId = cartId;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
}
