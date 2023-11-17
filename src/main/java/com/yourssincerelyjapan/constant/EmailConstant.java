package com.yourssincerelyjapan.constant;

public enum EmailConstant {

    ;
    public static final String HOST = "http://localhost:8080";
    public static final String VERIFICATION_URL = "/users/account-verification?token=%s";
    public static final String TEXT_VERIFICATION_CONTENT = "Hello, %s,\n\n" +
            "Your account has been created. Please, click the link below to verify your account\n\n" +
            "%s.";

    public static final String SUBJECT_WELCOME = "Thank you for creating your account and welcome to Yours Sincerely, Japan";
    public static final String YOURS_SINCERELY_JAPAN = "Yours Sincerely Japan";
    public static final String NAME = "name";
    public static final String URL = "url";
    public static final String VERIFY_EMAIL_TEMPLATE = "verify-email";
    public static final String EMAIL_ENCODING = "UTF-8";
}
