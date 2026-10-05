package com.example.payment_platform.payment.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IdempotancyRecord {
    @Id
    private String idempotencyKey;
    private String paymentId;
    private Instant createdDate;
    private String requestHash;
}
