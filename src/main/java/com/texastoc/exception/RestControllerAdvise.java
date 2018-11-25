package com.texastoc.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@ControllerAdvice
public class RestControllerAdvise extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
            new Date(), "Validation Failed",
            ex.getBindingResult().toString());
        return new ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = { RuntimeException.class })
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
        System.out.println("!!! " + ex.getClass().getName());
        String bodyOfResponse = "This should be application specific";
        return handleExceptionInternal(ex, bodyOfResponse,
            new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

}