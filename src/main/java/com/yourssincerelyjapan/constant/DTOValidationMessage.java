package com.yourssincerelyjapan.constant;

public enum DTOValidationMessage {

    ;

    public static final String MANDATORY_FIELD = "The field is mandatory.";
    public static final String FULL_NAME_LENGTH = "Full name must be between 1 and 30 characters.";
    public static final String VALID_EMAIL = "Enter a valid email address.";
    public static final String EMAIL_ALREADY_IN_USE = "Email is already in use!";
    public static final String EMAIL_REGEX = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&’*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
    public static final String PASSWORD_LENGTH = "Password must be between 8 and 20 characters long.";
    public static final String PASSWORD_SPECIAL_SYMBOL = "Password does not meet the requirements.";
    public static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#%&$*]).*$";
    public static final String PASSWORD_DOES_NOT_MATCH = "Password does not match!";
}
