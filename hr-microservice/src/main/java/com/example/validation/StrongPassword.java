package com.example.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;



@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Pattern.List({
	 @Pattern(regexp="^.*\\d+.*$",message="{validation.strongPassword2}"),
     @Pattern(regexp="^.*[-_]+.*$",message="{validation.strongPassword3}")
})
@Size(max=8)
@Constraint(validatedBy = {})
public @interface StrongPassword {
	   String message() default "{validation.strongPassword1}";
	    Class<?>[] groups() default {};
	    Class<? extends Payload>[] payload() default {};
}
