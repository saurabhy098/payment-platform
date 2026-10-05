package com.example.payment_platform.payment.repository;

import com.example.payment_platform.payment.domain.IdempotancyRecord;
import com.example.payment_platform.payment.domain.Payment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface IdempotancyRepository extends MongoRepository<IdempotancyRecord,String> {
    Optional<IdempotancyRecord> findByRequestHash(String requestHash);

}
