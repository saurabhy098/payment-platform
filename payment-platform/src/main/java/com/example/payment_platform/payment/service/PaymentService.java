package com.example.payment_platform.payment.service;


import com.example.payment_platform.payment.domain.IdempotancyRecord;
import com.example.payment_platform.payment.domain.Payment;
import com.example.payment_platform.payment.dto.CreatePaymentRequestDto;
import com.example.payment_platform.payment.dto.PaymentDto;
import com.example.payment_platform.payment.enums.PaymentStatus;
import com.example.payment_platform.payment.exception.DuplicatePaymentException;
import com.example.payment_platform.payment.exception.IdempotencyKeyReuseException;
import com.example.payment_platform.payment.exception.InvalidPaymentStateException;
import com.example.payment_platform.payment.exception.NoPaymentFoundException;
import com.example.payment_platform.payment.helper.HashingHelper;
import com.example.payment_platform.payment.mapper.PaymentMapper;
import com.example.payment_platform.payment.repository.IdempotancyRepository;
import com.example.payment_platform.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final IdempotancyRepository idempotancyRepository;

    public Payment createPayment(CreatePaymentRequestDto paymentRequestDto,String idempotencyKey) {
      Optional<IdempotancyRecord> idempotencyRecord = idempotancyRepository.findById(idempotencyKey);
      String hashcode =HashingHelper.GenerateRequestedHashcode(paymentRequestDto);

        if(idempotencyRecord.isPresent()){

            if(idempotencyRecord.get().getRequestHash().equals(hashcode)){

                String duplicatePayment=  idempotencyRecord.get().getPaymentId();
                Optional<Payment> paymentExists=paymentRepository.findById(duplicatePayment);

                return paymentExists.get();
            }else{
                throw new IdempotencyKeyReuseException("Idempotency key is being reused");
            }
        }
        Optional<Payment> existingPayment=paymentRepository.findByMerchantReference(paymentRequestDto.getMerchantReference());
        if(existingPayment.isPresent() ){
        throw new DuplicatePaymentException("Record already exists");
    }
        Payment payment=  paymentMapper.map(paymentRequestDto);
        Instant now = Instant.now();

        payment.setStatus(PaymentStatus.CREATED);
        payment.setPaymentId(UUID.randomUUID().toString());
        payment.setUserId("User-11223344");
        payment.setCreatedAt(now);
        payment.setUpdatedAt(now);

        payment =  paymentRepository.save(payment);

        IdempotancyRecord record =new  IdempotancyRecord();

        record.setIdempotencyKey(idempotencyKey);
        record.setPaymentId(payment.getPaymentId());
        record.setCreatedDate(Instant.now());
        record.setRequestHash(hashcode);

        idempotancyRepository.save(record);

        return payment;
    }
    public PaymentDto findPayment(String paymentId){
        Optional<Payment> paymentExists=paymentRepository.findById(paymentId);
        if(paymentExists.isEmpty()){
            throw new NoPaymentFoundException("Payment not found");
        }
        return paymentMapper.map(paymentExists.get());
    }

    public List<PaymentDto> findAllPayments(){
        List<Payment> paymentExists=paymentRepository.findAll();
        return paymentExists.stream().map(paymentMapper::map).toList();
    }

    public PaymentDto cancelPayment(String paymentId){
        Optional<Payment> paymentExists=paymentRepository.findById(paymentId);
        if(paymentExists.isEmpty()){
            throw new NoPaymentFoundException("Payment not found");
        }
        if(PaymentStatus.CREATED.equals(paymentExists.get().getStatus())){
            paymentExists.get().setStatus(PaymentStatus.CANCELLED);
            paymentExists.get().setUpdatedAt(Instant.now());
            return paymentMapper.map(paymentRepository.save(paymentExists.get()));
        }
        else
        {
            throw new InvalidPaymentStateException("Payment exists but cannot be cancelled due to its state");

        }
    }
}


