package com.brian.springboot.ecommerceproject.model;

import java.util.List;
import java.util.Map;

public class AllReviewForFronted {
	private ReviewForFronted userReview;
	
	private List<ReviewForFronted> otherReviews;
	
	private double averageRating;
	
	private int reviewCount;
	
	private Map<Integer, Long> ratingCountMap;

	public AllReviewForFronted() {
	}
	
	public AllReviewForFronted(double averageRating, int reviewCount) {
		this.averageRating = averageRating;
		this.reviewCount = reviewCount;
	}

	public AllReviewForFronted(ReviewForFronted userReivew, List<ReviewForFronted> otherReviews, double averageRating,
			int reviewCount, Map<Integer, Long> ratingCountMap) {
		this.userReview = userReivew;
		this.otherReviews = otherReviews;
		this.averageRating = averageRating;
		this.reviewCount = reviewCount;
		this.ratingCountMap = ratingCountMap;
	}


	public ReviewForFronted getUserReview() {
		return userReview;
	}

	public void setUserReview(ReviewForFronted userReview) {
		this.userReview = userReview;
	}

	public List<ReviewForFronted> getOtherReviews() {
		return otherReviews;
	}

	public void setOtherReviews(List<ReviewForFronted> otherReviews) {
		this.otherReviews = otherReviews;
	}

	public double getAverageRating() {
		return averageRating;
	}

	public void setAverageRating(double averageRating) {
		this.averageRating = averageRating;
	}

	public int getReviewCount() {
		return reviewCount;
	}

	public void setReviewCount(int reviewCount) {
		this.reviewCount = reviewCount;
	}

	public Map<Integer, Long> getRatingCountMap() {
		return ratingCountMap;
	}

	public void setRatingCountMap(Map<Integer, Long> ratingCountMap) {
		this.ratingCountMap = ratingCountMap;
	}

}
