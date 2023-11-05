package com.yourssincerelyjapan.events.listener;

import com.yourssincerelyjapan.model.entity.User;
import com.yourssincerelyjapan.model.entity.UserAccountConfirmation;
import com.yourssincerelyjapan.events.OnRegistrationCompleteEvent;
import com.yourssincerelyjapan.service.UserAccountConfirmationService;
import com.yourssincerelyjapan.service.EmailService;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

    private final UserAccountConfirmationService confirmationService;
    private final EmailService emailService;

    public RegistrationListener(UserAccountConfirmationService confirmationService, EmailService emailService) {

        this.confirmationService = confirmationService;
        this.emailService = emailService;
    }

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {

        final User user = event.getUser();

        final UserAccountConfirmation confirmation = new UserAccountConfirmation(user);

        this.confirmationService.saveVerificationToken(confirmation);

        try {
            this.emailService.sendHtmlVerificationEmail(user.getFullName(), user.getEmail(), confirmation.getToken());
        } catch (UnsupportedEncodingException e) {
            System.out.println(e.getMessage());
        }

    }
}
