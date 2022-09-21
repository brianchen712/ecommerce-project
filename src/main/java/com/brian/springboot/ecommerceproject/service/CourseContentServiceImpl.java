package com.brian.springboot.ecommerceproject.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.brian.springboot.ecommerceproject.dao.ReviewRepository;
import com.brian.springboot.ecommerceproject.entity.Course;
import com.brian.springboot.ecommerceproject.entity.Review;
import com.brian.springboot.ecommerceproject.entity.User;
import com.brian.springboot.ecommerceproject.model.ReviewInfo;

@Service
public class CourseContentServiceImpl implements CourseContentService {
	
	private ReviewRepository reviewRepostory;
	
	public CourseContentServiceImpl(ReviewRepository reviewRepostory) {
		this.reviewRepostory = reviewRepostory;
	}

	@Override
	@Transactional
	public ReviewInfo getReviews(User user, Optional<Course> optCourse) {
		List<Review> allrReviews = null;
		Review userReview = null;
		List<Review> otherReviews = null;
		
		if(optCourse.isPresent()) {
			allrReviews = reviewRepostory.findByCourseId(optCourse.get().getId());
			
			if(Optional.ofNullable(allrReviews).isPresent()) {
				userReview = allrReviews.stream().filter(review->review.getUser().getId() == user.getId()).findAny().orElse(null);
				otherReviews = allrReviews.stream().filter(review->review.getUser().getId() != user.getId()).collect(Collectors.toList());
			}
		}
		return new ReviewInfo(allrReviews,userReview, otherReviews);
	}
	
	@Override
	@Transactional
	public void saveReview(Review review) {
		reviewRepostory.save(review);
	}

}
