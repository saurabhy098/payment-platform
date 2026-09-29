package com.example.payment_platform.payment.service;


import com.example.payment_platform.payment.domain.Payment;
import com.example.payment_platform.payment.dto.CreatePaymentRequestDto;
import com.example.payment_platform.payment.enums.PaymentStatus;
import com.example.payment_platform.payment.exception.DuplicatePaymentException;
import com.example.payment_platform.payment.mapper.PaymentMapper;
import com.example.payment_platform.payment.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service

public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    public PaymentService(PaymentRepository paymentRepository, PaymentMapper paymentMapper) {
        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
    }
    public Payment createPayment(CreatePaymentRequestDto paymentRequestDto) {
        Optional<Payment> existingPayment=paymentRepository.findByMerchantReference(paymentRequestDto.getMerchantReference());
    if(existingPayment.isPresent()){
        throw new DuplicatePaymentException("Record already exists");
    }
        Payment payment=  paymentMapper.map(paymentRequestDto);
        Instant now = Instant.now();
        payment.setStatus(PaymentStatus.CREATED);
        payment.setPaymentId(UUID.randomUUID().toString());
        payment.setUserId("User-11223344");
        payment.setCreatedAt(now);
        payment.setUpdatedAt(now);

       Payment savedPayment= paymentRepository.save(payment);

        return savedPayment;
    }
}
