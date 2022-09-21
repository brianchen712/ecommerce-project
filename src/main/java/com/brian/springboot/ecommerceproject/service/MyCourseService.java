package com.brian.springboot.ecommerceproject.service;

import java.util.List;

import com.brian.springboot.ecommerceproject.entity.Course;

public interface MyCourseService {
	public List<Course> getCourses(List<Integer> shoppingCartIds);
}
