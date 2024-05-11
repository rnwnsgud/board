package store.ppingpong.board.common.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import store.ppingpong.board.common.ResponseDto;
import store.ppingpong.board.common.handler.exception.*;
import store.ppingpong.board.common.handler.exception.file.FileNotDeletedException;
import store.ppingpong.board.common.handler.exception.file.FileNotSupportedException;
import store.ppingpong.board.common.handler.exception.file.FileUploadException;
import store.ppingpong.board.common.handler.exception.join.CertificationCodeNotMatchedException;
import store.ppingpong.board.common.handler.exception.join.EmailNotSupportedException;
import store.ppingpong.board.common.handler.exception.resource.*;


import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseDto<?>> resourceNotFoundException(ResourceNotFoundException e) {
        return new ResponseEntity<>(ResponseDto.of(-1, e.getMessage()), NOT_FOUND);
    }

    @ExceptionHandler(EmailNotSupportedException.class)
    public ResponseEntity<ResponseDto<?>> emailNotSupportException(EmailNotSupportedException e) {
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

    @ExceptionHandler(ResourceInactiveException.class)
    public ResponseEntity<ResponseDto<?>> resourceInactiveException(ResourceInactiveException e) {
        return new ResponseEntity<>(ResponseDto.of(-1,e.getMessage()), FORBIDDEN);
    }

    @ExceptionHandler(FileUploadException.class)
    public ResponseEntity<ResponseDto<?>> fileUploadException(FileUploadException e) {
        return new ResponseEntity<>(ResponseDto.of(-1,e.getMessage()), BAD_REQUEST);
    }

    @ExceptionHandler(FileNotDeletedException.class)
    public ResponseEntity<ResponseDto<?>> fileNotDeletedException(FileNotDeletedException e) {
        return new ResponseEntity<>(ResponseDto.of(-1,e.getMessage()), BAD_REQUEST);
    }

    @ExceptionHandler(FileNotSupportedException.class)
    public ResponseEntity<ResponseDto<?>> fileNotSupportedException(FileNotSupportedException e) {
        return new ResponseEntity<>(ResponseDto.of(-1,e.getMessage()), BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotOwnerException.class)
    public ResponseEntity<ResponseDto<?>> resourceNotOwnerException(ResourceNotOwnerException e) {
        return new ResponseEntity<>(ResponseDto.of(-1,e.getMessage()), BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotVerifiedException.class)
    public ResponseEntity<ResponseDto<?>> resourceNotVerifiedException(ResourceNotVerifiedException e) {
        return new ResponseEntity<>(ResponseDto.of(-1,e.getMessage()), UNAUTHORIZED);
    }

}
