package edu.sharif.webproject.config;

import edu.sharif.webproject.security.exception.InvalidApiTokenException;
import edu.sharif.webproject.security.exception.UsernameAlreadyInUseException;
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

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Username already in use!")  // 400
    @ExceptionHandler(UsernameAlreadyInUseException.class)
    public void handleUsernameAlreadyInUseException() {
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "API token not found!")  // 400
    @ExceptionHandler(InvalidApiTokenException.class)
    public void handleInvalidApiKeyRequestException() {
    }
}
