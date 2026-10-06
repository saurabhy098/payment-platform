package com.example.payment_platform.payment.dto;

import com.example.payment_platform.payment.domain.Payment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentCreationResult {
     Payment payment;
      Boolean created=false;
}
