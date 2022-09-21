package com.brian.springboot.ecommerceproject.model;

public class InstructorForFronted {
	private int id;
	
	private String name;
	
	private String education;
	
	private String experience;
	
	private String email;
	
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

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getExperience() {
		String[] experiences = experience.split(" ");
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < experiences.length; i++) {
			if(i % 2 == 0) {
				 sb.append(experiences[i]).append(" ");
			} else {
				 sb.append(experiences[i]).append("<br>");
			}
		}
		experience = sb.toString();
		return experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
}
