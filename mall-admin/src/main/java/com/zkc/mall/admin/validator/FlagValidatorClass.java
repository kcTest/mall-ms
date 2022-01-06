package com.zkc.mall.admin.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FlagValidatorClass implements ConstraintValidator<FlagValidator, Integer> {
	
	private String[] values;
	
	@Override
	public void initialize(FlagValidator constraintAnnotation) {
		this.values = constraintAnnotation.value();
	}
	
	@Override
	public boolean isValid(Integer value, ConstraintValidatorContext context) {
		boolean isValid = false;
		if (value == null) {
			return true;
		}
		for (String s : values) {
			if (s.equals(String.valueOf(value))) {
				isValid = true;
				break;
			}
		}
		return isValid;
	}
}
