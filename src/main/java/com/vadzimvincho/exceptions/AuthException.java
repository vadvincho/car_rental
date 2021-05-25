package com.vinchovadzim.exceptions;

import javax.security.sasl.AuthenticationException;

public class AuthException extends AuthenticationException {
    public AuthException(String message, Exception e) {
        super(message, e);
    }

    public AuthException(String message) {
        super(message);
    }
}
