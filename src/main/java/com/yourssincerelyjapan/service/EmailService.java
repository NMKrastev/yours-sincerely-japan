package com.yourssincerelyjapan.service;

import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;

public interface EmailService {

    void sendMail(String emailAddress, String title, String emailMessage) throws MessagingException, UnsupportedEncodingException;
}
