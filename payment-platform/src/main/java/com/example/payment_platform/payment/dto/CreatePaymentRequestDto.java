package com.example.payment_platform.payment.dto;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class CreatePaymentRequestDto {
    private String merchantReference;
    private BigDecimal amount;
    private String currency;
    private String paymentMethod;
}
