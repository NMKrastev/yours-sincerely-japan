package com.yourssincerelyjapan.utils;

import static com.yourssincerelyjapan.constant.EmailConstant.*;

public class EmailUtils {

    public static String getEmailMessage(String name, String token) {

        return String.format(TEXT_VERIFICATION_CONTENT, name, getVerificationUrl(token));
    }

    public static String getVerificationUrl(String token) {
        return String.format(HOST + VERIFICATION_URL, token);
    }
}
