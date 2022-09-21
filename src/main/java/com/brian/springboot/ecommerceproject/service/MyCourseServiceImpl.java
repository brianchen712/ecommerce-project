package com.brian.springboot.ecommerceproject.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.brian.springboot.ecommerceproject.dao.CourseRepository;
import com.brian.springboot.ecommerceproject.entity.Course;

@Service
public class MyCourseServiceImpl implements MyCourseService {
	
	private CourseRepository courseRepository;

	
	public MyCourseServiceImpl(CourseRepository courseRepository) {
		this.courseRepository = courseRepository;
	}

	@Override
	@Transactional
	public List<Course> getCourses(List<Integer> shoppingCartIds) {
		
		List<Course> courses = courseRepository.findByIdIn(shoppingCartIds);

		return courses;
	}

}
