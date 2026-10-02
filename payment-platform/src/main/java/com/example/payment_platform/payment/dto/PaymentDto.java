package com.example.payment_platform.payment.dto;

import com.example.payment_platform.payment.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto {
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
