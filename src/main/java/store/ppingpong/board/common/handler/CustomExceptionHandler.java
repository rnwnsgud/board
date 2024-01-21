package store.ppingpong.board.common.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import store.ppingpong.board.common.handler.exception.*;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class CustomExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public String resourceNotFoundException(ResourceNotFoundException e) {
        log.error(e.getMessage());
        return e.getMessage();
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(EmailNotSupportException.class)
    public String emailNotSupportException(EmailNotSupportException e) {
        log.error(e.getMessage());
        return e.getMessage();
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public String validationException(ValidationException e) {
        log.error(e.getMessage());
        return e.getMessage();
    }

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(ResourceAlreadyExistException.class)
    public String resourceAlreadyExistException(ResourceAlreadyExistException e) {
        log.error(e.getMessage());
        return e.getMessage();
    }

    @ResponseStatus(FORBIDDEN)
    @ExceptionHandler(CertificationCodeNotMatchedException.class)
    public String certificationCodeNotMatchedException(CertificationCodeNotMatchedException e) {
        log.error(e.getMessage());
        return e.getMessage();
    }

}
