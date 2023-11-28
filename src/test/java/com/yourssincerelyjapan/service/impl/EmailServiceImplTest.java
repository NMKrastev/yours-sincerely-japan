package com.yourssincerelyjapan.service.impl;

import com.yourssincerelyjapan.config.EmailConfiguration;
import com.yourssincerelyjapan.model.dto.ContactDTO;
import com.yourssincerelyjapan.model.entity.Email;
import com.yourssincerelyjapan.repository.EmailRepository;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

class EmailServiceImplTest {

    @Mock
    private EmailRepository emailRepository;

    @Mock
    private EmailConfiguration emailConfiguration;

    @Mock
    private JavaMailSender javaMailSender;

    @Mock
    private TemplateEngine templateEngine;

    @Mock
    private EmailServiceImpl emailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        emailService = new EmailServiceImpl(
                emailRepository, emailConfiguration, javaMailSender, templateEngine
        );
    }

    @Test
    void sendSimpleEmail() {

        when(emailConfiguration.getUsername()).thenReturn("test@example.com");

        MimeMessage mimeMessage = mock(MimeMessage.class);
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);

        MimeMessageHelper mimeMessageHelper = mock(MimeMessageHelper.class);
        when(mimeMessageHelper.getMimeMessage()).thenReturn(mimeMessage);
        doNothing().when(javaMailSender).send(mimeMessage);

        Email sentEmail = new Email();
        sentEmail.setEmailFrom("sender@example.com");
        sentEmail.setEmailTo("test@example.com");
        sentEmail.setEmailSubject("Message from John Doe");
        sentEmail.setEmailContent("Hello, this is a test message.");
        sentEmail.setReceivedOn(LocalDateTime.now());
        sentEmail.setReceived(true);

        when(emailRepository.save(any(Email.class))).thenReturn(sentEmail);

        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setEmail("sender@example.com");
        contactDTO.setFullName("John Doe");
        contactDTO.setContactContent("Hello, this is a test message.");
        emailService.sendSimpleEmail(contactDTO);

        verify(javaMailSender, times(1)).send(mimeMessage);
        verify(emailRepository, times(1)).save(any(Email.class));
    }

    @Test
    void sendHtmlVerificationEmail() throws UnsupportedEncodingException {

        when(emailConfiguration.getUsername()).thenReturn("test@example.com");

        // Mock javaMailSender createMimeMessage method
        MimeMessage mimeMessage = mock(MimeMessage.class);
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);

        // Mock MimeMessageHelper constructor
        MimeMessageHelper mimeMessageHelper = mock(MimeMessageHelper.class);
        when(mimeMessageHelper.getMimeMessage()).thenReturn(mimeMessage);
        doNothing().when(javaMailSender).send(mimeMessage);

        // Mock TemplateEngine process method
        when(templateEngine.process(any(String.class), any(Context.class))).thenReturn("Email content");

        // Mock Email constructor
        Email sentEmail = new Email();
        sentEmail.setEmailFrom("test@example.com");
        sentEmail.setEmailTo("recipient@example.com");
        sentEmail.setEmailSubject("Welcome to Yours Sincerely Japan");
        sentEmail.setEmailContent("Email content");
        sentEmail.setCreatedOn(LocalDateTime.now());
        sentEmail.setSent(true);

        // Mock emailRepository save method
        when(emailRepository.save(any(Email.class))).thenReturn(sentEmail);

        // Test sendHtmlVerificationEmail method
        emailService.sendHtmlVerificationEmail("John Doe", "recipient@example.com", "token");

        // Verify that javaMailSender.send is called
        verify(javaMailSender, times(1)).send(mimeMessage);
        // Verify that emailRepository.save is called
        verify(emailRepository, times(1)).save(any(Email.class));
    }
}