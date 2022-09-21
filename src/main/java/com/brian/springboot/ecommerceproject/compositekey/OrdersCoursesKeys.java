package com.brian.springboot.ecommerceproject.compositekey;

import java.io.Serializable;

/**
 * joinTable OrdersCourses的複合鍵
 * @author 88696
 *
 */
public class OrdersCoursesKeys implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int orderId;
	
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
