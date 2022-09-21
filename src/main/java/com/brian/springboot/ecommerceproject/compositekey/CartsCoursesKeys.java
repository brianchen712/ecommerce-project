package com.brian.springboot.ecommerceproject.compositekey;

import java.io.Serializable;

/**
 * joinTable CartsCourses的複合鍵
 * @author 88696
 *
 */
public class CartsCoursesKeys implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int cartId;
	
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
