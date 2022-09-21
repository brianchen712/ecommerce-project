package com.brian.springboot.ecommerceproject.model;

public class OrderForFronted {
	
	private int id;
	
	private String courseSize;
	
	private String purchaseDate;
	
	private String amount;
	
	private String creditCard;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCourseSize() {
		return courseSize;
	}

	public void setCourseSize(String courseSize) {
		this.courseSize = courseSize;
	}

	public String getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(String purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(String creditCard) {
		this.creditCard = creditCard;
	}
}
