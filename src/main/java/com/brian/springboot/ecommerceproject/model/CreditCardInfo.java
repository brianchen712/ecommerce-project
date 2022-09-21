package com.brian.springboot.ecommerceproject.model;

import java.util.List;

import com.brian.springboot.ecommerceproject.entity.CreditCard;

public class CreditCardInfo {
	
	private int cardSize;
	
	private List<CreditCard> creditCards;
	
	//使用者預設的信用卡
	private CreditCard defaultCard;
	
	public CreditCardInfo() {
	
	}

	public CreditCardInfo(int cardSize, List<CreditCard> creditCards, CreditCard defaultCard) {
		this.cardSize = cardSize;
		this.creditCards = creditCards;
		this.defaultCard = defaultCard;
	}

	public int getCardSize() {
		return cardSize;
	}

	public void setCardSize(int cardSize) {
		this.cardSize = cardSize;
	}

	public List<CreditCard> getCreditCards() {
		return creditCards;
	}

	public void setCreditCards(List<CreditCard> creditCards) {
		this.creditCards = creditCards;
	}

	public CreditCard getDefaultCard() {
		return defaultCard;
	}

	public void setDefaultCard(CreditCard defaultCard) {
		this.defaultCard = defaultCard;
	}
}
