package com.elice.homealone.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HomealoneException.class)
    public ResponseEntity<String> handleHomealoneException(HomealoneException ex) {
        HttpStatus status = ex.getErrorCode().getHttpStatus();
        return new ResponseEntity<>(ex.getMessage(), status);
    }
}
