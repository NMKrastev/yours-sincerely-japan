package com.yourssincerelyjapan.service.impl;

import com.yourssincerelyjapan.config.EmailConfiguration;
import com.yourssincerelyjapan.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public class EmailServiceImpl implements EmailService {

    private final EmailConfiguration emailConfiguration;

    private final JavaMailSender javaMailSender;

    public EmailServiceImpl(EmailConfiguration emailConfiguration, JavaMailSender javaMailSender) {

        this.emailConfiguration = emailConfiguration;
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendMail(String emailAddress, String title, String emailMessage) throws MessagingException, UnsupportedEncodingException {

        final MimeMessage message = this.javaMailSender.createMimeMessage();

        final MimeMessageHelper helper = new MimeMessageHelper(message, true);

        final InternetAddress fromAddress = new InternetAddress(this.emailConfiguration.getUsername(), "Yours Sincerely Japan");

        helper.setFrom(fromAddress);
        helper.setTo(emailAddress);
        helper.setSubject(title);
        helper.setText(emailMessage);

        this.javaMailSender.send(message);
    }
}
