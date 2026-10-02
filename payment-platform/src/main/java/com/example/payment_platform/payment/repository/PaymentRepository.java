package com.example.payment_platform.payment.repository;

import com.example.payment_platform.payment.domain.Payment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PaymentRepository extends MongoRepository<Payment,String> {

    Optional<Payment> findByMerchantReference(String merchantReference);
}
