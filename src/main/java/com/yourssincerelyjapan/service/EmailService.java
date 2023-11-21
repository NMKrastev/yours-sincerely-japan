package com.yourssincerelyjapan.service;

import java.io.UnsupportedEncodingException;

public interface EmailService {

    void sendSimpleEmail(String name, String from, String message);

    void sendHtmlVerificationEmail(String name, String to, String token) throws UnsupportedEncodingException;

}
