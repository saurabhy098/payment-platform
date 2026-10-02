package com.example.payment_platform.payment.service;


import com.example.payment_platform.payment.domain.Payment;
import com.example.payment_platform.payment.dto.CreatePaymentRequestDto;
import com.example.payment_platform.payment.dto.PaymentDto;
import com.example.payment_platform.payment.enums.PaymentStatus;
import com.example.payment_platform.payment.exception.DuplicatePaymentException;
import com.example.payment_platform.payment.exception.InvalidPaymentStateException;
import com.example.payment_platform.payment.exception.NoPaymentFoundException;
import com.example.payment_platform.payment.mapper.PaymentMapper;
import com.example.payment_platform.payment.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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

        return paymentRepository.save(payment);
    }
    public PaymentDto findPayment(String paymentId){
        Optional<Payment> paymentExists=paymentRepository.findById(paymentId);
        if(paymentExists.isEmpty()){
            throw new NoPaymentFoundException("Payment not found");
        }
        return paymentMapper.map(paymentExists.get());
    }

    public List<PaymentDto> findAllPayments(){
        List<Payment> paymentExists=paymentRepository.findAll();
        return paymentExists.stream().map(paymentMapper::map).toList();
    }

    public PaymentDto cancelPayment(String paymentId){
        Optional<Payment> paymentExists=paymentRepository.findById(paymentId);
        if(paymentExists.isEmpty()){
            throw new NoPaymentFoundException("Payment not found");
        }
        if(PaymentStatus.CREATED.equals(paymentExists.get().getStatus())){
            paymentExists.get().setStatus(PaymentStatus.CANCELLED);
            paymentExists.get().setUpdatedAt(Instant.now());
            return paymentMapper.map(paymentRepository.save(paymentExists.get()));
        }
        else
        {
            throw new InvalidPaymentStateException("Payment exists but cannot be cancelled due to its state");

        }
    }
}


