package com.yourssincerelyjapan.service;

import java.io.UnsupportedEncodingException;

public interface EmailService {

    void sendHtmlVerificationEmail(String name, String to, String token) throws UnsupportedEncodingException;

}
