package com.example.payment_platform.payment.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class CreatePaymentRequestDto {
    @NotBlank
    private String merchantReference;
    @Positive
    private BigDecimal amount;
    @NotBlank @Size(max=3)
    private String currency;
    @NotBlank
    private String paymentMethod;
}
