package com.brian.springboot.ecommerceproject.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanWrapperImpl;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {
	
	private String firstFieldName;
    private String secondFieldName;
    private String message;

    @Override
    public void initialize(final FieldMatch constraintAnnotation) {
	    firstFieldName = constraintAnnotation.first();
	    secondFieldName = constraintAnnotation.second();
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        boolean valid = true;

        final Object firstObj = new BeanWrapperImpl(value).getPropertyValue(firstFieldName);//取得欄位1內容
        final Object secondObj = new BeanWrapperImpl(value).getPropertyValue(secondFieldName);//取得欄位2內容
        // 比較欄位1跟欄位2的內容
        valid =  firstObj == null && secondObj == null || firstObj != null && firstObj.equals(secondObj);

        if (!valid){
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(firstFieldName)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }

        return valid;
    }
	
}