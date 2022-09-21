package com.brian.springboot.ecommerceproject.model;

import java.util.List;

import com.brian.springboot.ecommerceproject.entity.Review;

public class ReviewInfo {
	
	private List<Review> allReviews;
	
	private Review userReview;
	
	private List<Review> otherReviews;

	public ReviewInfo() {
	}

	public ReviewInfo(List<Review> allReviews, Review userReview, List<Review> otherReviews) {
		this.allReviews = allReviews;
		this.userReview = userReview;
		this.otherReviews = otherReviews;
	}

	public List<Review> getAllReviews() {
		return allReviews;
	}

	public void setAllReviews(List<Review> allReviews) {
		this.allReviews = allReviews;
	}

	public Review getUserReview() {
		return userReview;
	}

	public void setUserReview(Review userReview) {
		this.userReview = userReview;
	}

	public List<Review> getOtherReviews() {
		return otherReviews;
	}

	public void setOtherReviews(List<Review> otherReviews) {
		this.otherReviews = otherReviews;
	}
	
}
