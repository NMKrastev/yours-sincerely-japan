package com.yourssincerelyjapan.service;

import com.yourssincerelyjapan.model.dto.ContactDTO;
import jakarta.mail.internet.AddressException;

import java.io.UnsupportedEncodingException;

public interface EmailService {

    void sendSimpleEmail(ContactDTO contactDTO);

    void sendHtmlVerificationEmail(String name, String to, String token) throws UnsupportedEncodingException;

}
