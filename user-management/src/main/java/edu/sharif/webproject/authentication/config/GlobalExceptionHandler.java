package edu.sharif.webproject.authentication.config;

import edu.sharif.webproject.authentication.security.exception.InvalidApiTokenException;
import edu.sharif.webproject.authentication.security.exception.UsernameAlreadyInUseException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUsernameNotFoundException(UsernameNotFoundException exception) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(UsernameAlreadyInUseException.class)
    public ResponseEntity<String> handleUsernameAlreadyInUseException(UsernameAlreadyInUseException exception) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler(InvalidApiTokenException.class)
    public ResponseEntity<String> handleInvalidApiKeyRequestException(InvalidApiTokenException exception) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }
}
