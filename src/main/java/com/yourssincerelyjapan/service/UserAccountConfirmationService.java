package com.yourssincerelyjapan.service;

import com.yourssincerelyjapan.model.entity.UserAccountConfirmation;

public interface UserAccountConfirmationService {

    void saveVerificationToken(UserAccountConfirmation confirmation);

    UserAccountConfirmation getVerificationToken(String token);

    boolean accountVerification(String token);

}
