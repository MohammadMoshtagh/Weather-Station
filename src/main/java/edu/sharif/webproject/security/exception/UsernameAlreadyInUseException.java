package edu.sharif.webproject.security.exception;

import org.springframework.security.core.AuthenticationException;

public class UsernameAlreadyInUseException extends AuthenticationException {

    public UsernameAlreadyInUseException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public UsernameAlreadyInUseException(String msg) {
        super(msg);
    }
}
