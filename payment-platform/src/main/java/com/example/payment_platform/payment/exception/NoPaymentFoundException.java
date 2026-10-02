package com.example.payment_platform.payment.exception;

public class NoPaymentFoundException extends RuntimeException {
    public NoPaymentFoundException(String message) {
        super(message);
    }
}
