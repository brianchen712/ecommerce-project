package com.brian.springboot.ecommerceproject.model;

public class CourseAndInstructorForFronted {
	private CourseForFronted course;
	
	private InstructorForFronted instructor;

	public CourseForFronted getCourse() {
		return course;
	}

	public void setCourse(CourseForFronted course) {
		this.course = course;
	}

	public InstructorForFronted getInstructor() {
		return instructor;
	}

	public void setInstructor(InstructorForFronted instructor) {
		this.instructor = instructor;
	}
	
	
}
