package com.brian.springboot.ecommerceproject.validator;

import java.util.Optional;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CardNoValidator implements ConstraintValidator<ValidCardNo, String> {

	private String[] messages;

	@Override
	public void initialize(ValidCardNo constraintAnnotation) {
		messages = constraintAnnotation.messages();
	}

	@Override
	public boolean isValid(String cardNo, ConstraintValidatorContext context) {
		if(Optional.ofNullable(cardNo).isPresent() && Optional.ofNullable(cardNo).get().length() == 16) {			
			// 檢核卡號格式是否正確
			if(!validCardNoFormat(cardNo)){
				context.buildConstraintViolationWithTemplate(messages[0])
	            .addConstraintViolation()
	            .disableDefaultConstraintViolation();
				return false;
			}
			// 檢核卡片是否為Visa、MasterCard
			if(!(cardNo.startsWith("4") || cardNo.startsWith("5"))) {
				context.buildConstraintViolationWithTemplate(messages[1])
	            .addConstraintViolation()
	            .disableDefaultConstraintViolation();
				return false;
			}
		}
		return true;
	}
	
	private boolean validCardNoFormat(String cardNo) {
		// (1) 前15碼數字加權加總(奇數權重2，偶數權重1)
		int sum = getSum(cardNo);
		// (2) 數字除以 10 取餘數，如果結果是 0，那檢查碼就是 0，否則就是用 10 減去之後的結果
		int checkNum = (sum % 10 == 0) ? sum % 10 : 10-(sum % 10);
		// (3) 檢查檢查碼跟卡號最後一碼是否一致
		boolean isValid = checkNum == Character.getNumericValue(cardNo.charAt(cardNo.length() - 1));
		return isValid;
	}

	/**
	 * 數字加權加總
	 * 
	 * @param cardNo
	 * @return
	 */
	private int getSum(String cardNo) {
		int sum = 0;
		// 將前15碼數字加權加總(奇數權重2，偶數權重1)，若加權數字大於9，則將個位數跟十位數相加
		for (int i = 0; i < cardNo.length() - 1; i++) {
			int num = Character.getNumericValue(cardNo.charAt(i));
			int result = 0;
			if (i % 2 == 0) {
				result += 2 * num;
				if (result > 9) {
					String resultString = String.valueOf(result);
					result = Character.getNumericValue(resultString.charAt(0))
							+ Character.getNumericValue(resultString.charAt(1));
				}
			} else {
				result = num;
			}

			sum += result;
		}
		return sum;
	}

}