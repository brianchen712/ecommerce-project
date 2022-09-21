package com.brian.springboot.ecommerceproject.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brian.springboot.ecommerceproject.dao.CreditCardRepository;
import com.brian.springboot.ecommerceproject.dao.UserRepository;
import com.brian.springboot.ecommerceproject.entity.CreditCard;
import com.brian.springboot.ecommerceproject.entity.User;

@Service
public class MyCreditCardServiceImpl implements MyCreditCardService {
	
	private UserRepository userRepository;

	private CreditCardRepository creditCardRepository;

	public MyCreditCardServiceImpl(UserRepository userRepository, CreditCardRepository creditCardRepository) {
		this.userRepository = userRepository;
		this.creditCardRepository = creditCardRepository;
	}

	@Override
	@Transactional
	public void setDefaultCreditCard(CreditCard curCard, CreditCard defaultCard) {
		// 將非預設信用卡改成預設信用卡
		if (Optional.ofNullable(curCard).isPresent()) {
			curCard.setDefaultFlag(true);
			creditCardRepository.save(curCard);
		}
		// 將預設信用卡改成非預設信用卡
		if (Optional.ofNullable(defaultCard).isPresent()) {
			defaultCard.setDefaultFlag(false);
			creditCardRepository.save(defaultCard);
		}
	}

	@Override
	@Transactional
	public void saveCreditCard(CreditCard creditCard) {
		creditCardRepository.save(creditCard);
	}

	@Override
	@Transactional
	public void removerCreditCard(User user, List<CreditCard> creditCards, CreditCard curCard) {
		creditCards.remove(curCard);
		user.setCreditCards(creditCards);
		userRepository.save(user);
	}

	@Override
	public List<CreditCard> getCards(User user) {
		List<CreditCard> cards = new ArrayList<>();
		if (Optional.ofNullable(user.getId()).isPresent()) {
			cards = creditCardRepository.findByUserId(Optional.ofNullable(user.getId()).get());
		}
		return cards;
	}

}
