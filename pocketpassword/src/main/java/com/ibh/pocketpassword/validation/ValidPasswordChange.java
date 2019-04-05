package com.ibh.pocketpassword.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { ValidPasswordChangeValidator.class })
@Documented
public @interface ValidPasswordChange {
	String message() default "The two passwords are not matched";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };
}
