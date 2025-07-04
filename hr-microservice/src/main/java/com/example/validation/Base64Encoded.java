package com.example.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;


@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy=Base64Validator.class)
public @interface Base64Encoded {
	String message() default "{validation.base64}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
