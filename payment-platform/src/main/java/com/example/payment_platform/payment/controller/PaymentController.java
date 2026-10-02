package com.example.payment_platform.payment.controller;

import com.example.payment_platform.payment.domain.Payment;
import com.example.payment_platform.payment.dto.CreatePaymentRequestDto;
import com.example.payment_platform.payment.dto.PaymentDto;
import com.example.payment_platform.payment.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    @PostMapping("/payments")
    public ResponseEntity<Payment> savePayment(@RequestBody @Valid CreatePaymentRequestDto createPaymentRequestDto){

        Payment payment = paymentService.createPayment(createPaymentRequestDto);
        URI uri=URI.create("/api/v1/payments/"+payment.getPaymentId());
        return ResponseEntity.created(uri).body(payment);

    }

    @GetMapping("/payments/{paymentId}")
    public ResponseEntity<PaymentDto> findPaymentById(@PathVariable String paymentId){
        PaymentDto payment = paymentService.findPayment(paymentId);
        return ResponseEntity.ok().body(payment);

    }

    @GetMapping("/payments")
    public ResponseEntity<List<PaymentDto>> findAllPayment(){
        List<PaymentDto> payments = paymentService.findAllPayments();
        return ResponseEntity.ok().body(payments);

    }
    @PostMapping("/payments/{paymentId}/cancel")
    public ResponseEntity<PaymentDto> cancelPayment(@PathVariable String paymentId){
        PaymentDto  payment = paymentService.cancelPayment(paymentId);
        return ResponseEntity.ok().body(payment);
    }
}
