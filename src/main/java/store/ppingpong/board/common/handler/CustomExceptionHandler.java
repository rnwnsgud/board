package store.ppingpong.board.common.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import store.ppingpong.board.common.ResponseDto;
import store.ppingpong.board.common.handler.exception.*;


import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseDto<?>> resourceNotFoundException(ResourceNotFoundException e) {
        return new ResponseEntity<>(ResponseDto.of(-1, e.getMessage()), NOT_FOUND);
    }

    @ExceptionHandler(EmailNotSupportException.class)
    public ResponseEntity<ResponseDto<?>> emailNotSupportException(EmailNotSupportException e) {
        return new ResponseEntity<>(ResponseDto.of(-1,e.getMessage()), BAD_REQUEST);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ResponseDto<?>> validationException(ValidationException e) {
        return new ResponseEntity<>(ResponseDto.of(-1,e.getMessage()), BAD_REQUEST);
    }

    @ExceptionHandler(ResourceAlreadyExistException.class)
    public ResponseEntity<ResponseDto<?>> resourceAlreadyExistException(ResourceAlreadyExistException e) {
        return new ResponseEntity<>(ResponseDto.of(-1,e.getMessage()), BAD_REQUEST);
    }

    @ExceptionHandler(CertificationCodeNotMatchedException.class)
    public ResponseEntity<ResponseDto<?>> certificationCodeNotMatchedException(CertificationCodeNotMatchedException e) {
        return new ResponseEntity<>(ResponseDto.of(-1,e.getMessage()), FORBIDDEN);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseDto<?>> requestDtoBindingException(HttpMessageNotReadableException e) {
        return new ResponseEntity<>(ResponseDto.of(-1,"요청 본문이 잘못되었거나, DTO로 변환할 수 없습니다."), BAD_REQUEST);
    }

}
