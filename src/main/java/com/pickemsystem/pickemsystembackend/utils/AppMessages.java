package com.pickemsystem.pickemsystembackend.utils;

public class AppMessages {

    // Errors messages
    public static final String INTERNAL_SERVER_ERROR = "An internal server error have occurred";

    // Success messages
    public static final String SUCCESSFUL_LOGIN = "Successful login";
    public static final String SUCCESSFUL_TOKEN_REFRESH = "Successful token refresh";
    public static final String REGION_CREATED = "Region created correctly";
    public static final String USER_VERIFIED = "Email verified correctly";
    public static final String EMAIL_CONFIRMATION_MAIL_SENT = "Confirmation mail sent correctly";
    public static final String EMAIL_PASSWORD_RESET_MAIL_SENT = "Password reset mail sent correctly";
    public static final String PASSWORD_RESET = "Your password was changed correctly";

    // Bad requests messages
    public static final String INVALID_TOKEN = "The given token is invalid";
    public static final String EXPIRED_TOKEN = "The given token expired";
    public static final String INVALID_REQUEST = "Theres one or more invalid values in request";
    public static final String INCORRECT_PASSWORD = "The given password is incorrect";
    public static final String USER_NOT_EXISTS = "The given user not exists";
    public static final String EMAIL_TAKEN = "This email is already registered";
    public static final String USERNAME_TAKEN = "This username is taken";
    public static final String USER_ALREADY_VERIFIED = "This user is already verified";

    // Not found messages
    public static final String SPORT_NOT_FOUND = "The given sport do not exists";
    public static final String EMAIL_NOT_FOUND = "We can't find a user with the provided email";
}
