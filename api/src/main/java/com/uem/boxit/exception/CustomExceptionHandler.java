package com.uem.boxit.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<Object> objectNotFound(ObjectNotFoundException e) {
        StandError apiError = new StandError(HttpStatus.NOT_FOUND, e.getLocalizedMessage(), "Resource Not Found");
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

}
