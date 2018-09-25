package com.uem.boxit.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<Object> objectNotFound(ObjectNotFoundException e) {
        StandError apiError = new StandError(HttpStatus.NOT_FOUND, e.getLocalizedMessage(), "Recurso não encontrado");
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler(MailException.class)
    public ResponseEntity<Object> mailException(MailException e) {
        StandError apiError = new StandError(HttpStatus.NOT_FOUND, e.getLocalizedMessage(), "Falha ao enviar email");
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler(CnpjJaExisteException.class)
    public ResponseEntity<Object> cnpjJaExiste(ObjectNotFoundException e) {
        StandError apiError = new StandError(HttpStatus.NOT_FOUND, e.getLocalizedMessage(), "CNPJ já cadastrado");
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        List<String> messages = new ArrayList<>(constraintViolations.size());
        messages.addAll(constraintViolations.stream()
                .map(constraintViolation -> String.format("%s", constraintViolation.getMessage()))
                .collect(Collectors.toList()));
        StandError apiError = new StandError(HttpStatus.BAD_REQUEST, e.getLocalizedMessage(), messages);
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }
}
