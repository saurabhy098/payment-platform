package com.example.payment_platform.payment.domain;

import com.example.payment_platform.payment.enums.PaymentStatus;
import com.example.payment_platform.payment.exception.InvalidPaymentStateException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PaymentTest {
    Payment payment;

    @Test
   public void  paymentChangeFromCreatedtoProcessed(){

        payment=new Payment();
        payment.setMerchantReference("ABC-10008");
        payment.setAmount(BigDecimal.valueOf(200.0));
        payment.setCurrency("USD");
        payment.setPaymentMethod("CARD");

        payment.changeStatus(PaymentStatus.PROCESSING);

        assertEquals(PaymentStatus.PROCESSING,payment.getStatus());
    }
    @Test
    public void  paymentChangeFromProcessedTOSuccess(){

        payment=new Payment();
        payment.setMerchantReference("ABC-10008");
        payment.setAmount(BigDecimal.valueOf(200.0));
        payment.setCurrency("USD");
        payment.setPaymentMethod("CARD");

        payment.changeStatus(PaymentStatus.PROCESSING);
        payment.changeStatus(PaymentStatus.SUCCESS);

        assertEquals(PaymentStatus.SUCCESS,payment.getStatus());
    }
    @Test
    public void  paymentChangeFromProcessedTOFailed(){

        payment=new Payment();
        payment.setMerchantReference("ABC-10008");
        payment.setAmount(BigDecimal.valueOf(200.0));
        payment.setCurrency("USD");
        payment.setPaymentMethod("CARD");

        payment.changeStatus(PaymentStatus.PROCESSING);
        payment.changeStatus(PaymentStatus.FAILED);

        assertEquals(PaymentStatus.FAILED,payment.getStatus());
    }

    @Test
    public void  paymentChangeFromFailedToProcessing(){

        payment=new Payment();
        payment.setMerchantReference("ABC-10008");
        payment.setAmount(BigDecimal.valueOf(200.0));
        payment.setCurrency("USD");
        payment.setPaymentMethod("CARD");

        payment.changeStatus(PaymentStatus.PROCESSING);
        payment.changeStatus(PaymentStatus.FAILED);
        payment.changeStatus(PaymentStatus.PROCESSING);

        assertEquals(PaymentStatus.PROCESSING,payment.getStatus());
    }
    @Test
    public void  paymentChangeFromCreatedToSuccess(){

        payment=new Payment();
        payment.setMerchantReference("ABC-10008");
        payment.setAmount(BigDecimal.valueOf(200.0));
        payment.setCurrency("USD");
        payment.setPaymentMethod("CARD");


        assertThrows( InvalidPaymentStateException.class,() -> payment.changeStatus(PaymentStatus.SUCCESS));

    }
    @Test
   public void paymentStartsInCreatedState() {
        Payment payment = new Payment();

        assertEquals(PaymentStatus.CREATED, payment.getStatus());
    }
    @Test
    public void  paymentChangeFromSuccessToNone(){

        payment=new Payment();
        payment.setMerchantReference("ABC-10008");
        payment.setAmount(BigDecimal.valueOf(200.0));
        payment.setCurrency("USD");
        payment.setPaymentMethod("CARD");

        payment.changeStatus(PaymentStatus.PROCESSING);
        payment.changeStatus(PaymentStatus.SUCCESS);
        assertThrows( InvalidPaymentStateException.class,() -> payment.changeStatus(PaymentStatus.PROCESSING));
    }
}
