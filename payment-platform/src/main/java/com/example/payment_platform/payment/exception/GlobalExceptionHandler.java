package com.example.payment_platform.payment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({DuplicatePaymentException.class, InvalidPaymentStateException.class})
    public ResponseEntity<ErrorResponse> handleException(RuntimeException e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ErrorResponse.builder()
                        .status(409)
                        .error(e instanceof DuplicatePaymentException ? "Payment already exists" : "Invalid Payment State")
                        .message(e.getMessage())
                        .build());
    }
    @ExceptionHandler(NoPaymentFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(NoPaymentFoundException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.builder()
                        .status(404)
                        .error("Payment not found")
                        .message("Payment not found")
                        .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException e)
    {
        Map<String, String> fieldErrors= new HashMap<>();
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            fieldErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse
                        .builder()
                        .fieldErrors(fieldErrors)
                        .status(400)
                        .error("Validation Failed")
                        .message("Request validation failed")
                        .build());
    }
}
