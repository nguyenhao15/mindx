package com.example.demo01.domains.mongo.MiniCrm.Payment.service;


import com.example.demo01.domains.mongo.MiniCrm.Payment.dtos.transaction.TransactionInfoDTO;
import com.example.demo01.domains.mongo.MiniCrm.Payment.dtos.transaction.TransactionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CentralPaymentService {

    private final List<PaymentProcessor> processors;

    public TransactionInfoDTO handlePayment(TransactionRequest paymentRequest) {
        PaymentProcessor processor = processors.stream()
                .filter(p -> p.support(paymentRequest))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid payment"));

        return processor.process(paymentRequest);
    }

}
