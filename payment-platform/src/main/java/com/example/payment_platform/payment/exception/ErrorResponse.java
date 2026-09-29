package com.example.payment_platform.payment.exception;

import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Map;

@Data
@Builder
public class ErrorResponse {
     Map<String, String> fieldErrors;
     Integer status;
     String message;
     String error;

}
