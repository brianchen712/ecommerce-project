package com.brian.springboot.ecommerceproject.service;

import java.util.List;
import java.util.Map;

import com.brian.springboot.ecommerceproject.entity.Course;
import com.brian.springboot.ecommerceproject.entity.User;
import com.brian.springboot.ecommerceproject.model.CartAndCourseInfo;

public interface ShoppingCartService {
	public List<Course> getCourseByIds(List<Integer> courseIds);

	public CartAndCourseInfo addShoppingCart(User user, int courseId) throws Exception;

	public Map<Integer, Integer> removeShoppingCart(int courseId, int orderId);

	public Course findCourseById(int courseId) throws Exception;
}
