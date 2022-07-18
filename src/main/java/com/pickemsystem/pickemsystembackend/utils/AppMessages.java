package com.pickemsystem.pickemsystembackend.utils;

public class AppMessages {

    // Errors messages
    public static final String INTERNAL_SERVER_ERROR = "An internal server error have occurred";

    // Success messages
    public static final String SUCCESSFUL_LOGIN = "Successful login";
    public static final String REGION_CREATED = "Region created correctly";
    public static final String USER_VERIFIED = "Email verified correctly";

    // Bad requests messages
    public static final String INVALID_EMAIL_TOKEN = "The given token is invalid";
    public static final String EXPIRED_EMAIL_TOKEN = "The given token expired";
    public static final String INVALID_REQUEST = "Theres one or more invalid values in request";
    public static final String INCORRECT_PASSWORD = "The given password is incorrect";
    public static final String USER_NOT_EXISTS = "The given user not exists";
    public static final String EMAIL_TAKEN = "This email is already registered";
    public static final String USERNAME_TAKEN = "This username is taken";
    public static final String USER_ALREADY_VERIFIED = "This user is already verified";
}
