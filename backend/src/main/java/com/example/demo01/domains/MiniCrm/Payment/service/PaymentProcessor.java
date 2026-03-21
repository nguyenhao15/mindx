package com.example.demo01.domains.MiniCrm.Payment.service;

import com.example.demo01.domains.MiniCrm.Payment.dtos.transaction.TransactionInfoDTO;
import com.example.demo01.domains.MiniCrm.Payment.dtos.transaction.TransactionRequest;


public interface PaymentProcessor {

    boolean support(TransactionRequest request);

    TransactionInfoDTO process(TransactionRequest request);
}
