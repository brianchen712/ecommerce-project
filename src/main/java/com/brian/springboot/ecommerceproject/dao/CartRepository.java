package com.brian.springboot.ecommerceproject.dao;

import java.sql.Timestamp;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.brian.springboot.ecommerceproject.entity.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
	// 取得近三天，特定使用者的購物車內容
	@Query(value = "select * from cart where user_id = ?1 and create_time between ?2 and ?3 and checkout_flag = 0", nativeQuery = true)
	Optional<Cart> findByUserIdAndDateAndFlag(int userId, Timestamp before3Days, Timestamp now);
}
