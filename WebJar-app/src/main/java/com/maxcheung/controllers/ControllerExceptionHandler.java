package com.maxcheung.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@RestControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<Object> handleIllegalArguments(RuntimeException ex, WebRequest request) {

        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    
    
    @ExceptionHandler(UnauthorisedVerifierException.class)
    protected ResponseEntity<Object> handleUnauthorisedVerifier(UnauthorisedVerifierException ex, WebRequest request) {
        ApiError apiError = new ApiError(HttpStatus.UNAUTHORIZED, "UnAuthorised exception occurred", ex.getLocalizedMessage());
        return handleExceptionInternal(ex, apiError, new HttpHeaders(), apiError.getStatus(), request);

    }

}
