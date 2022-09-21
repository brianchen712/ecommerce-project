package com.brian.springboot.ecommerceproject.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.brian.springboot.ecommerceproject.entity.Instructor;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Integer> {

}
