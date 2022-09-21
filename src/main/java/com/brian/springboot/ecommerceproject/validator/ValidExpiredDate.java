package com.brian.springboot.ecommerceproject.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = ExpiredDateValidator.class)
@Target({ ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidExpiredDate {
	String message() default "";
	
	String[] messages() default {"到期日格式有誤","到期日需大於今日"};

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
