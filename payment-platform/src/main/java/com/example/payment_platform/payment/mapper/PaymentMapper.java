package com.example.payment_platform.payment.mapper;

import com.example.payment_platform.payment.domain.Payment;
import com.example.payment_platform.payment.dto.CreatePaymentRequestDto;
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
}
