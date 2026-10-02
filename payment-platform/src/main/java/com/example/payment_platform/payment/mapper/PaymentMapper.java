package com.example.payment_platform.payment.mapper;

import com.example.payment_platform.payment.domain.Payment;
import com.example.payment_platform.payment.dto.CreatePaymentRequestDto;
import com.example.payment_platform.payment.dto.PaymentDto;
import org.springframework.stereotype.Component;


@Component
public class PaymentMapper {
    public Payment map(CreatePaymentRequestDto createPaymentRequestDto){
        return Payment
                .builder()
                .merchantReference(createPaymentRequestDto.getMerchantReference())
                .amount(createPaymentRequestDto.getAmount())
                .currency(createPaymentRequestDto.getCurrency())
                .paymentMethod(createPaymentRequestDto.getPaymentMethod())
                .build();
    }

    public PaymentDto map(Payment payment){
        return PaymentDto
                .builder()
                .paymentId(payment.getPaymentId())
                .merchantReference(payment.getMerchantReference())
                .amount(payment.getAmount())
                .currency(payment.getCurrency())
                .paymentMethod(payment.getPaymentMethod())
                .createdAt(payment.getCreatedAt())
                .updatedAt(payment.getUpdatedAt())
                .status(payment.getStatus())
                .userId(payment.getUserId())
                .build();
    }
}
