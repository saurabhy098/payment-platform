package com.example.payment_platform.payment.helper;

import com.example.payment_platform.payment.dto.CreatePaymentRequestDto;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

public class HashingHelper {
    public static String generateRequestedHash(CreatePaymentRequestDto paymentRequestDto){
        String Hash;
        String input=paymentRequestDto.getMerchantReference()+"|"+paymentRequestDto.getAmount().toString()+"|"+paymentRequestDto.getCurrency()+"|"+paymentRequestDto.getPaymentMethod();
        try{
            MessageDigest digest=MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes=digest.digest(input.getBytes(StandardCharsets.UTF_8));
            Hash= HexFormat.of().formatHex(hashedBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return Hash;
    }


}
