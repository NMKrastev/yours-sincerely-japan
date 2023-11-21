package com.yourssincerelyjapan.validation;

import com.yourssincerelyjapan.config.EmailConfiguration;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SiteEmailMatchValidator implements ConstraintValidator<SiteEmailMatch, String> {

    private final EmailConfiguration emailConfiguration;

    public SiteEmailMatchValidator(EmailConfiguration emailConfiguration) {

        this.emailConfiguration = emailConfiguration;
    }


    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {

        return !this.emailConfiguration.getUsername().equals(email);
    }
}
