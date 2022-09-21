package com.brian.springboot.ecommerceproject.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.brian.springboot.ecommerceproject.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{
	 @Query(value = "select * from role where name = ?1", nativeQuery = true)
	 Optional<Role> findRoleByName(String name);
}
