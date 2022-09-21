package com.brian.springboot.ecommerceproject.form;

import javax.validation.constraints.NotNull;

public class ReviewForm {
	
	@NotNull(message = "卡號必填")
	private String comment;

	
	@NotNull(message = "評分必填")
	private String rating;
	
	public ReviewForm() {
	
	}
	
	public ReviewForm(String comment, String rating) {
		this.comment = comment;
		this.rating = rating;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}	

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

}
