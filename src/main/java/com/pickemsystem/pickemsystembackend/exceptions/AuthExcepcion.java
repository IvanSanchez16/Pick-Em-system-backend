package com.pickemsystem.pickemsystembackend.exceptions;

import org.springframework.security.core.AuthenticationException;

public class AuthExcepcion extends AuthenticationException {

    public AuthExcepcion(String msg, Throwable cause) {
        super(msg, cause);
    }

    public AuthExcepcion(String msg) {
        super(msg);
    }
}
