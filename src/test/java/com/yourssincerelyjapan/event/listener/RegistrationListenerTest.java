package com.yourssincerelyjapan.event.listener;

import com.yourssincerelyjapan.event.OnRegistrationCompleteEvent;
import com.yourssincerelyjapan.model.entity.User;
import com.yourssincerelyjapan.model.entity.UserAccountConfirmation;
import com.yourssincerelyjapan.service.EmailService;
import com.yourssincerelyjapan.service.UserAccountConfirmationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RegistrationListenerTest {

    @Mock
    private UserAccountConfirmationService confirmationService;

    @Mock
    private EmailService emailService;

    @Mock
    private RegistrationListener registrationListener;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        registrationListener = new RegistrationListener(confirmationService, emailService);
    }

    @Test
    void onApplicationEvent() throws UnsupportedEncodingException {

        User user = new User();
        user.setFullName("John Doe");
        user.setEmail("john.doe@example.com");

        UserAccountConfirmation confirmation = new UserAccountConfirmation(user);
        confirmation.setToken("verificationToken");

        OnRegistrationCompleteEvent event = new OnRegistrationCompleteEvent(user);

        doNothing().when(emailService).sendHtmlVerificationEmail(user.getFullName(), user.getEmail(), confirmation.getToken());

        registrationListener.onApplicationEvent(event);

    }
}