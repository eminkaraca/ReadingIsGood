package com.readingisgood.Service.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import org.springframework.web.bind.MethodArgumentNotValidException;

@ControllerAdvice(basePackages = "com.readingisgood.Service.Controller")
public class ExceptionHandling extends ResponseEntityExceptionHandler {

    @ExceptionHandler(PersistedBeforeException.class)
    public ResponseEntity<?> handleRigException(PersistedBeforeException ex) {
        return new ResponseEntity<>(ExceptionResponse.builder().code(ex.getResponse()).message(ex.getMessage()).build()
                , ex.getResponse());
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleSystemException(Exception ex) {
        ExceptionResponse response = ExceptionResponse.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(response, response.getCode());
    }


}
