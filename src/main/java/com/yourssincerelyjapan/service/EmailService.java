package com.yourssincerelyjapan.service;

import java.io.UnsupportedEncodingException;

public interface EmailService {

    //void sendMail(String emailAddress, String title, String emailMessage) throws MessagingException, UnsupportedEncodingException;

    void sendSimpleVerificationEmail(String name, String to, String token) throws UnsupportedEncodingException;

    void sendMimeEmailWithAttachments(String name, String to, String token) throws UnsupportedEncodingException;

    void sendHtmlVerificationEmail(String name, String to, String token) throws UnsupportedEncodingException;

}
