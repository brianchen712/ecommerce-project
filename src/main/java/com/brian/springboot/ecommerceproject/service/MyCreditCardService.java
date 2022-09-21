package com.brian.springboot.ecommerceproject.service;

import java.util.List;

import com.brian.springboot.ecommerceproject.entity.CreditCard;
import com.brian.springboot.ecommerceproject.entity.User;

public interface MyCreditCardService {
	
	public void setDefaultCreditCard(CreditCard curCreditCard, CreditCard defaultCard);
	
	public void saveCreditCard(CreditCard creditCard);
	
	public void removerCreditCard(User user, List<CreditCard> creditCards, CreditCard curCard);
	
	public List<CreditCard> getCards(User user);
}
