package com.yourssincerelyjapan.validation;

import com.yourssincerelyjapan.repository.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UserEmailExistValidator implements ConstraintValidator<UserEmailExist, String> {

    private final UserRepository userRepository;

    public UserEmailExistValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        return this.userRepository
                .findByEmail(value)
                .isPresent();
    }
}
