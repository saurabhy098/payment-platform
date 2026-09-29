package com.example.payment_platform.payment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicatePaymentException.class)
    public ResponseEntity<?> handleException(DuplicatePaymentException e) {return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());}
}
