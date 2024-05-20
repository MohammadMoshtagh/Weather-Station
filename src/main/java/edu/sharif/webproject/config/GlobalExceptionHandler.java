package edu.sharif.webproject.config;

import edu.sharif.webproject.security.exception.InvalidApiKeyRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Username not found!")  // 403
    @ExceptionHandler(UsernameNotFoundException.class)
    public void handleUsernameNotFoundException() {
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Username not found!")  // 400
    @ExceptionHandler(InvalidApiKeyRequest.class)
    public void handleUsernameAlreadyInUseException() {
    }
}
