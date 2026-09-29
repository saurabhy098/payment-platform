package com.example.payment_platform.payment.controller;

import com.example.payment_platform.payment.domain.Payment;
import com.example.payment_platform.payment.dto.CreatePaymentRequestDto;
import com.example.payment_platform.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    @PostMapping("/payments")
    public ResponseEntity<Payment> savePayment(@RequestBody CreatePaymentRequestDto createPaymentRequestDto){

        Payment payment = paymentService.createPayment(createPaymentRequestDto);
        URI uri=URI.create("/api/v1/payments/"+payment.getPaymentId());
        return ResponseEntity.created(uri).body(payment);

    }
}
