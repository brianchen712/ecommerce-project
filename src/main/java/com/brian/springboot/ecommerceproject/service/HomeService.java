package com.brian.springboot.ecommerceproject.service;

import com.brian.springboot.ecommerceproject.entity.User;
import com.brian.springboot.ecommerceproject.model.CourseInfo;

public interface HomeService {
	
	public CourseInfo getCoursesInfo(User user);
	
	public CourseInfo getCoursesInfo(User user, int typeId) throws Exception ;
	
	//public List<Course> findByCourseType(int id) throws Exception ;
	
}
