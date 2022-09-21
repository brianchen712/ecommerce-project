package com.brian.springboot.ecommerceproject.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.brian.springboot.ecommerceproject.entity.CreditCard;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, Integer> {
	@Query(value = "select * from credit_card where user_id = ?1", nativeQuery = true)
	List<CreditCard> findByUserId(int userId);
	
	@Query(value = "select * from credit_card where credit_card_no = ?1", nativeQuery = true)
	Optional<CreditCard> findByCardNo(String cardNo);
}
