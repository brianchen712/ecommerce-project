package com.brian.springboot.ecommerceproject.validator;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<ValidEmail, String> {

	private Pattern pattern;
	private Matcher matcher;
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	@Override
	public boolean isValid(final String email, final ConstraintValidatorContext context) {
		boolean isValid = true;
		if(Optional.ofNullable(email).isPresent()) {
			if(!email.isEmpty()) {
				pattern = Pattern.compile(EMAIL_PATTERN);
				matcher = pattern.matcher(email);
				isValid = matcher.matches();
			}
		}
		
		return isValid;
	}

}