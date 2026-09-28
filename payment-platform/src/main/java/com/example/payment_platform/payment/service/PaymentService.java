package com.example.payment_platform.payment.service;


import com.example.payment_platform.payment.domain.Payment;
import com.example.payment_platform.payment.dto.CreatePaymentRequestDto;
import com.example.payment_platform.payment.exception.DuplicatePaymentException;
import com.example.payment_platform.payment.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service

public class PaymentService {

    private final PaymentRepository paymentRepository;
    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }
    public String createPayment(CreatePaymentRequestDto paymentRequestDto) {
        Optional<Payment> payment=paymentRepository.findByMerchantReference(paymentRequestDto.getMerchantReference());
    if(payment.isPresent()){
        throw new DuplicatePaymentException("Record already exists");
    }

        return null;
    }
}
