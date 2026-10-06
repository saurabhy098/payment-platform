package com.example.payment_platform.payment.exception;

public class IdempotencyRecordInconsistencyException extends RuntimeException {
    public IdempotencyRecordInconsistencyException(String message) {
        super(message);
    }
}
