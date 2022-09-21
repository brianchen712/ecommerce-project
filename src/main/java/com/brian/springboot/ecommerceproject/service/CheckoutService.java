package com.brian.springboot.ecommerceproject.service;

import com.brian.springboot.ecommerceproject.entity.CreditCard;
import com.brian.springboot.ecommerceproject.entity.Order;
import com.brian.springboot.ecommerceproject.entity.User;
import com.brian.springboot.ecommerceproject.form.CheckoutForm;


public interface CheckoutService {
	
	public void saveCheckout(CheckoutForm checkoutForm,CreditCard creditCard, User user, int cartId, Order order);
}
