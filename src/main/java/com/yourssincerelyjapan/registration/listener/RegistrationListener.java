package com.yourssincerelyjapan.registration.listener;

import com.yourssincerelyjapan.model.entity.User;
import com.yourssincerelyjapan.model.entity.UserAccountConfirmation;
import com.yourssincerelyjapan.registration.OnRegistrationCompleteEvent;
import com.yourssincerelyjapan.service.UserAccountConfirmationService;
import com.yourssincerelyjapan.service.EmailService;
import com.yourssincerelyjapan.service.UserService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

    private final UserService userService;
    private final MessageSource message;
    private final UserAccountConfirmationService confirmationService;
    private final EmailService emailService;

    public RegistrationListener(UserService userService, MessageSource message,
                                UserAccountConfirmationService confirmationService, EmailService emailService) {
        this.userService = userService;
        this.message = message;
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
