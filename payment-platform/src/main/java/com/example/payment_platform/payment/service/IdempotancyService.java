package com.example.payment_platform.payment.service;

import com.example.payment_platform.payment.domain.IdempotancyRecord;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class IdempotancyService {
    public IdempotancyRecord createIdempotancyRecord(IdempotancyRecord idempotancyRecord){
      return  IdempotancyRecord.builder()
                .idempotencyKey(idempotancyRecord.getIdempotencyKey())
                .paymentId(idempotancyRecord.getPaymentId())
              .createdDate(Instant.now())
              .requestHash(idempotancyRecord.getRequestHash())
        .build();
    }
}
