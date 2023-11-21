package com.yourssincerelyjapan.constant;

public enum DTOValidationMessage {

    ;

    public static final String MANDATORY_FIELD = "The field is mandatory.";
    public static final String FIELD_MUST_NOT_BE_EMPTY = "The field must not be empty.";
    public static final String FULL_NAME_LENGTH = "Full name must be between 1 and 30 characters.";
    public static final String VALID_EMAIL = "Enter a valid email address.";
    public static final String EMAIL_ALREADY_IN_USE = "Email is already in use!";
    public static final String EMAIL_REGEX = "(?:[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?\\.)+[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-zA-Z0-9-]*[a-zA-Z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    public static final String PASSWORD_LENGTH = "Password must be between 8 and 20 characters long.";
    public static final String PASSWORD_SPECIAL_SYMBOL = "Password does not meet the requirements.";
    public static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#%&$*]).*$";
    public static final String PASSWORD_DOES_NOT_MATCH = "Password does not match!";
    public static final String ARTICLE_TITLE_LENGTH = "Title must be between 1 and 50 characters.";
    public static final String ARTICLE_CONTENT_LENGTH = "Your story must be at least 20 characters.";
    public static final String ARTICLE_CATEGORIES_SELECT = "You must select at least one category.";
    public static final String CHOOSE_ROLE = "Choose a role!";
    public static final String COMMENT_LENGTH = "Comment length must be at least 1 character long.";
}
