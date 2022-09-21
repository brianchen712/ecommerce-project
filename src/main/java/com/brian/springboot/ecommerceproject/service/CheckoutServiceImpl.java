package com.brian.springboot.ecommerceproject.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brian.springboot.ecommerceproject.dao.CartRepository;
import com.brian.springboot.ecommerceproject.dao.CreditCardRepository;
import com.brian.springboot.ecommerceproject.dao.OrderRepository;
import com.brian.springboot.ecommerceproject.entity.Cart;
import com.brian.springboot.ecommerceproject.entity.CreditCard;
import com.brian.springboot.ecommerceproject.entity.Order;
import com.brian.springboot.ecommerceproject.entity.User;
import com.brian.springboot.ecommerceproject.form.CheckoutForm;

@Service
public class CheckoutServiceImpl implements CheckoutService {

	private CreditCardRepository creditCardRepository;
	
	private OrderRepository orderRepository;
	
	private CartRepository cartRepository;
	
	public CheckoutServiceImpl(CreditCardRepository creditCardRepository, OrderRepository orderRepository,
			CartRepository cartRepository) {
		this.creditCardRepository = creditCardRepository;
		this.orderRepository = orderRepository;
		this.cartRepository = cartRepository;
	}

	@Override
	@Transactional
	public void saveCheckout(CheckoutForm checkoutForm,CreditCard creditCard, User user, int cartId, Order order) {
		// 新增的卡片才需要儲存信用卡資訊
		if(checkoutForm.getId() == 0) {
			Optional<CreditCard> optCreditCard = creditCardRepository.findById(creditCard.getId());
			if(!optCreditCard.isPresent()) {
				creditCardRepository.save(creditCard);
			}
		}
		
		// 儲存訂單內容
		if(Optional.ofNullable(order).isPresent()) {
			orderRepository.save(order);
		}
		
		// 更新購物車資訊
		Optional<Cart> optCart = cartRepository.findById(cartId);
		if(optCart.isPresent()) {
			Cart cart = optCart.get();
			cart.setCheckoutFlag(true);
			cartRepository.save(cart);
		}
		
	}

}
