package com.example.validation;

import java.util.Base64;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class Base64Validator implements ConstraintValidator<Base64Encoded, String> {

	@Override
	public void initialize(Base64Encoded context) {
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null)
			return false;
		try {
			Base64.getDecoder().decode(value);
		} catch (IllegalArgumentException e) {
			return false;
		}
		return true;
	}

}
