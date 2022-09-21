package com.brian.springboot.ecommerceproject.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = CardNoValidator.class)
@Target({ ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidCardNo {
	String message() default "";
	
	String[] messages() default {"卡號格式有誤", "僅支援Visa、MasterCard卡別"};

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
