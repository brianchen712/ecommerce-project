package com.brian.springboot.ecommerceproject.service;

import java.util.Optional;

import com.brian.springboot.ecommerceproject.entity.Course;
import com.brian.springboot.ecommerceproject.entity.Review;
import com.brian.springboot.ecommerceproject.entity.User;
import com.brian.springboot.ecommerceproject.model.ReviewInfo;

public interface CourseContentService {
	
	public ReviewInfo getReviews(User user, Optional<Course> optCourse);
	public void saveReview(Review review);
}
