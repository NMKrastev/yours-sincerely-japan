package com.yourssincerelyjapan.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = SiteEmailMatchValidator.class)
public @interface SiteEmailMatch {

    String message() default "The email you've chosen is not applicable.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
