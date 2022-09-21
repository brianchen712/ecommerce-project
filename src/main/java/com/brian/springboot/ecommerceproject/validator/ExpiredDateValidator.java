package com.brian.springboot.ecommerceproject.validator;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ExpiredDateValidator implements ConstraintValidator<ValidExpiredDate, String> {
	private String[] messages;

	@Override
	public void initialize(ValidExpiredDate constraintAnnotation) {
		messages = constraintAnnotation.messages();
	}

	@Override
	public boolean isValid(String expiredDate, ConstraintValidatorContext context) {
		if(Optional.ofNullable(expiredDate).isPresent() && Optional.ofNullable(expiredDate).get().length() == 4) {			
			// 檢核到期日格式是否正確
			if(!validExpiredDateFormat(expiredDate)){
				context.buildConstraintViolationWithTemplate(messages[0])
	            .addConstraintViolation()
	            .disableDefaultConstraintViolation();
				return false;
			}
			// 檢核到期日是否大於今日
			if(!validExpiredDatePeriod(expiredDate)) {
				context.buildConstraintViolationWithTemplate(messages[1])
	            .addConstraintViolation()
	            .disableDefaultConstraintViolation();
				return false;
			}
		}
		return true;
	}
	
	private boolean validExpiredDateFormat(String expiredDate) {
		String month = expiredDate.substring(0, 2);
		
		Map<String, Integer> monthMap = getMonthMap();
		
		boolean isValid = monthMap.containsKey(month) ? true : false;
		
		return isValid;
	}
	
	private boolean validExpiredDatePeriod(String expiredDate) {
		// 取得系統時間的Calendar
		Calendar cal = Calendar.getInstance();
		
		// 設定到期日的Calendar
		Map<String, Integer> monthMap = getMonthMap();
		int expiredDateMonth = monthMap.get(expiredDate.substring(0, 2));
		int expiredDateYear = getFullExpiredDateYear(cal.get(Calendar.YEAR), expiredDate.substring(2, 4));
		Calendar expiredDateCal = Calendar.getInstance();
		expiredDateCal.set(expiredDateYear, expiredDateMonth, 1);
		
		// 查看到期日是否大於系統時間
		boolean isValid = cal.before(expiredDateCal);
		
		return isValid;
	}
	
	private Map<String, Integer> getMonthMap() {
		Map<String, Integer> monthMap = new HashMap<>();
		monthMap.put("01", 1);
		monthMap.put("02", 2);
		monthMap.put("03", 3);
		monthMap.put("04", 4);
		monthMap.put("05", 5);
		monthMap.put("06", 6);
		monthMap.put("07", 7);
		monthMap.put("08", 8);
		monthMap.put("09", 9);
		monthMap.put("10", 10);
		monthMap.put("11", 11);
		monthMap.put("12", 12);
		return monthMap;
	}
	
	private int getFullExpiredDateYear(int systemYear, String expiredDateYear) {
		String y = String.valueOf(systemYear);
		String fullExpiredDateYear = new StringBuffer(y.substring(0,2)).append(expiredDateYear).toString();
		return Integer.parseInt(fullExpiredDateYear);
	}

}