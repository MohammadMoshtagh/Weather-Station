package edu.sharif.webproject.security.exception;


import org.springframework.security.core.AuthenticationException;

public class InvalidApiTokenException extends AuthenticationException {

    public InvalidApiTokenException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public InvalidApiTokenException(String msg) {
        super(msg);
    }
}
