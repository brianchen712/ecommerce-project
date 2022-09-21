package com.brian.springboot.ecommerceproject.service;

import java.util.List;
import java.util.Optional;

import com.brian.springboot.ecommerceproject.entity.CartsCourses;
import com.brian.springboot.ecommerceproject.entity.Course;
import com.brian.springboot.ecommerceproject.entity.CourseType;
import com.brian.springboot.ecommerceproject.entity.CreditCard;
import com.brian.springboot.ecommerceproject.entity.Order;
import com.brian.springboot.ecommerceproject.model.CreditCardInfo;

public interface CommonService {
	
	public List<CartsCourses> getCartsCourses(int userId);
	
	public List<Order> getOrders(int userId);

	public List<CourseType> getCourseTypes();

	public Optional<Course> findCourseById(int courseId) throws Exception;
	
	public List<Course> findCourseByIds(List<Integer> courseIds);
	
	public CreditCardInfo getCreditCardInfo(int userId);
	
	public Optional<CreditCard> findCreditCard(String cardNo);
}
