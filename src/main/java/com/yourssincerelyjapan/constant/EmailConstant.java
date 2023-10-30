package com.yourssincerelyjapan.constant;

public enum EmailConstant {

    ;
    public static final String HOST = "http://localhost:8080";
    public static final String VERIFICATION_URL = "/users/account-verification?token=%s";
    public static final String TEXT_VERIFICATION_CONTENT = "Hello, %s,\n\n" +
            "Your account has been created. Please, click the link below to verify your account\n\n" +
            "%s.";

    public static final String SUBJECT_WELCOME = "Thank you for creating your account and welcome to Yours Sincerely, Japan";
}
