package com.brian.springboot.ecommerceproject.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.brian.springboot.ecommerceproject.entity.CourseType;

@Repository
public interface CourseTypeRepository extends JpaRepository<CourseType, Integer> {
}
