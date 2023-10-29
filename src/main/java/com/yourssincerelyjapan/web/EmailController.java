package com.yourssincerelyjapan.web;

import com.yourssincerelyjapan.service.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.UnsupportedEncodingException;

@Controller
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/sendMail")
    public String sendMail() throws MessagingException, UnsupportedEncodingException {

        //{recipientEmail} - {emailTitle} = {emailMessage}
        this.emailService.sendMail("{recipientEmail}", "{emailTitle}", "{emailMessage}");

        return "redirect:/";
    }
}
