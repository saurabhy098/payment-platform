package com.example.payment_platform.payment.domain;


import com.example.payment_platform.payment.enums.PaymentStatus;
import com.example.payment_platform.payment.exception.InvalidPaymentStateException;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;

@Document
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    @Id
    private String paymentId;
    private String userId;
    private String merchantReference;
    private BigDecimal amount;
    private String currency;
    private String paymentMethod;
    @Setter(AccessLevel.NONE)
    @Builder.Default
    private PaymentStatus status=PaymentStatus.CREATED;
    private Instant createdAt;
    private Instant updatedAt;

    public void changeStatus(PaymentStatus newStatus){
        if(status.equals(PaymentStatus.CREATED)){
            if(newStatus == PaymentStatus.PROCESSING || newStatus == PaymentStatus.CANCELLED){
                status=newStatus;
            }
            else throw new InvalidPaymentStateException("Invalid Payment Status");
        }
       else if(status.equals(PaymentStatus.PROCESSING)){
            if(newStatus == PaymentStatus.SUCCESS || newStatus == PaymentStatus.FAILED){
                status=newStatus;
            }
            else throw new InvalidPaymentStateException("Invalid Payment Status");
        }
        else if(status.equals(PaymentStatus.FAILED)){
            if(newStatus == PaymentStatus.PROCESSING){
                status=newStatus;
            }
            else throw new InvalidPaymentStateException("Invalid Payment Status");
        }
        else if (status.equals(PaymentStatus.SUCCESS)||status.equals(PaymentStatus.CANCELLED)){
                throw new InvalidPaymentStateException("Invalid Payment Status");

        }
    }
}
