package com.yourssincerelyjapan.service.impl;

import com.yourssincerelyjapan.model.entity.User;
import com.yourssincerelyjapan.model.entity.UserAccountConfirmation;
import com.yourssincerelyjapan.repository.ConfirmationRepository;
import com.yourssincerelyjapan.service.UserAccountConfirmationService;
import com.yourssincerelyjapan.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserAccountConfirmationServiceImpl implements UserAccountConfirmationService {

    private final ConfirmationRepository confirmationRepository;
    private final UserService userService;

    public UserAccountConfirmationServiceImpl(ConfirmationRepository confirmationRepository, UserService userService) {
        this.confirmationRepository = confirmationRepository;
        this.userService = userService;
    }

    @Override
    public void saveVerificationToken(UserAccountConfirmation confirmation) {
            this.confirmationRepository.save(confirmation);
    }

    @Override
    public UserAccountConfirmation getVerificationToken(String token) {
        return this.confirmationRepository.findByToken(token);
    }

    @Override
    public boolean accountVerification(String token) {

        final UserAccountConfirmation confirmation = this.getVerificationToken(token);

        if (confirmation == null) {
            return false;
        }

        final User user = confirmation.getUser();

        user.setEnabled(true);
        this.userService.saveEnabledUser(user);

        this.deleteVerificationToken(confirmation);

        return true;
    }

    private void deleteVerificationToken(UserAccountConfirmation confirmation) {

        this.confirmationRepository.delete(confirmation);
    }
}
