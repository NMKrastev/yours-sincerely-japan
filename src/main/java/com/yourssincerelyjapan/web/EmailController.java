package com.yourssincerelyjapan.web;

import com.yourssincerelyjapan.service.impl.EmailServiceImpl;
import org.springframework.stereotype.Controller;

@Controller
public class EmailController {

    private final EmailServiceImpl emailService;

    public EmailController(EmailServiceImpl emailService) {
        this.emailService = emailService;
    }

    /*@PostMapping("/sendMail")
    public String sendMail() throws MessagingException, UnsupportedEncodingException {

        //{recipientEmail} - {emailTitle} - {emailMessage}
        this.emailService.sendMail("{recipientEmail}", "{emailTitle}", "{emailMessage}");

        return "redirect:/";
    }*/
}
