package com.brian.springboot.ecommerceproject.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brian.springboot.ecommerceproject.dao.CourseRepository;
import com.brian.springboot.ecommerceproject.dao.CourseTypeRepository;
import com.brian.springboot.ecommerceproject.dao.OrdersCoursesRepository;
import com.brian.springboot.ecommerceproject.dao.ReviewRepository;
import com.brian.springboot.ecommerceproject.entity.Course;
import com.brian.springboot.ecommerceproject.entity.CourseType;
import com.brian.springboot.ecommerceproject.entity.OrdersCourses;
import com.brian.springboot.ecommerceproject.entity.Review;
import com.brian.springboot.ecommerceproject.entity.User;
import com.brian.springboot.ecommerceproject.model.CourseInfo;

@Service
public class HomeServiceImpl implements HomeService {

	private CourseRepository courseRepository;

	private CourseTypeRepository courseTypeRepository;

	private ReviewRepository reviewRepository;

	private OrdersCoursesRepository ordersCoursesRepository;

	public HomeServiceImpl(CourseRepository courseRepository, CourseTypeRepository courseTypeRepository,
			ReviewRepository reviewRepository, OrdersCoursesRepository ordersCoursesRepository) {
		this.courseRepository = courseRepository;
		this.courseTypeRepository = courseTypeRepository;
		this.reviewRepository = reviewRepository;
		this.ordersCoursesRepository = ordersCoursesRepository;
	}

	@Override
	@Transactional
	public CourseInfo getCoursesInfo(User user) {
		// 取得所有課程
		List<Course> courses = courseRepository.findAll();
		List<Integer> courseIds = courses.stream().map(course -> course.getId()).collect(Collectors.toList());

		// 取得所有課程的評論
		List<Review> reviews = reviewRepository.findByCourseIdIn(courseIds);

		// 取得所有訂單跟課程
		List<OrdersCourses> ordersCourses = ordersCoursesRepository.findByCourseIdIn(courseIds);

		return new CourseInfo(courses, reviews, ordersCourses);
	}

	@Override
	@Transactional
	public CourseInfo getCoursesInfo(User user, int typeId) throws Exception {
		Optional<CourseType> optCourseType = courseTypeRepository.findById(typeId);
		if (!optCourseType.isPresent()) {
			throw new Exception("課程類別編號不存在");
		}
		List<Course> courses = optCourseType.get().getCourses();
		List<Integer> courseIds = courses.stream().map(course -> course.getId()).collect(Collectors.toList());

		List<Review> reviews = reviewRepository.findByCourseIdIn(courseIds);

		List<OrdersCourses> ordersCourses = ordersCoursesRepository.findByCourseIdIn(courseIds);

		return new CourseInfo(courses, reviews, ordersCourses);
	}

}
