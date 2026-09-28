package com.example.payment_platform.payment.domain;


import com.example.payment_platform.payment.enums.PaymentStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;

@Document
public class Payment {
    @Id
    private String paymentId;
    private String userId;
    private String merchantReference;
    private BigDecimal amount;
    private String currency;
    private String paymentMethod;
    private PaymentStatus status;
    private Instant createdAt;
    private Instant updatedAt;
}
