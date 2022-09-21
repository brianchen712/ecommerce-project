package com.brian.springboot.ecommerceproject.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.brian.springboot.ecommerceproject.compositekey.CartsCoursesKeys;
import com.brian.springboot.ecommerceproject.entity.CartsCourses;

@Repository
public interface CartsCoursesRepository extends JpaRepository<CartsCourses, CartsCoursesKeys> {
	@Query(value = "select * from carts_courses where cart_id = ?1 ", nativeQuery = true)
	List<CartsCourses> findByCartId(int cartId);

	@Modifying
	@Query(value = "delete from carts_courses where cart_id = ?1 and course_id=?2", nativeQuery = true)
	void deleteByCartAndCourseId(int cartId, int courseId);
}
