package com.brian.springboot.ecommerceproject.model;

import java.text.DecimalFormat;

public class CourseForFronted {
	private int id;
	
	private String name;
	
	private String description;
	
	private String price;
	
	private String discountPrice;
	
	private String image;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = toWestNumFormat(price);
	}

	public String getDiscountPrice() {
		return discountPrice;
	}

	public void setDiscountPrice(int discountPrice) {
		this.discountPrice = toWestNumFormat(discountPrice);
	}

	public String getImage() {
		return image;
	}

	public void setImage(String Image) {
		this.image = Image;
	}
	
	public String toWestNumFormat(int num) {
		DecimalFormat df = new DecimalFormat("#,###");
		return df.format(num);
	}
}
