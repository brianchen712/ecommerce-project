package com.brian.springboot.ecommerceproject.entity;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="instructor")
public class Instructor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="full_name")
	private String fullName;
	
	@Column(name="email")
	private String email;
	
	@Column(name="create_date")
	private Timestamp createDate;
	
	@Lob
	@Column(name = "image",columnDefinition = "MEDIUMBLOB")
	private String image;
	
	@Column(name="education")
	private String education;
	
	@Column(name="experience")
	private String experience;
	
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "instructor", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
	private List<Course> courses;
	
	public Instructor() {
		
	}


	public Instructor(String fullName, String email, Timestamp createDate, String image, String education,
			String experience) {
		this.fullName = fullName;
		this.email = email;
		this.createDate = createDate;
		this.image = image;
		this.education = education;
		this.experience = experience;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getExperience() {
		return experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}

	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}
	
}
