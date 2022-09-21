package com.brian.springboot.ecommerceproject.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.brian.springboot.ecommerceproject.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
	@Query(value = "select * from review where course_id = ?1", nativeQuery = true)
	List<Review> findByCourseId(int courseId);
	
	@Query(value = "select * from review where course_id in ?1 ", nativeQuery = true)
	List<Review> findByCourseIdIn(List<Integer> courseIds);
}
