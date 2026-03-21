package com.example.demo01.domains.MiniCrm.Payment.service.impl;

import com.example.demo01.domains.MiniCrm.Contract.dtos.paymentCycles.PaymentCycleDTO;
import com.example.demo01.domains.MiniCrm.Contract.service.PaymentCycleService;
import com.example.demo01.domains.MiniCrm.Payment.dtos.transaction.TransactionInfoDTO;
import com.example.demo01.domains.MiniCrm.Payment.dtos.transaction.TransactionRequest;
import com.example.demo01.domains.MiniCrm.Payment.dtos.transaction.UpdatePaymentTermDetail;
import com.example.demo01.domains.MiniCrm.Payment.mapper.TransactionMapper;
import com.example.demo01.domains.MiniCrm.Payment.models.TransactionAllocate;
import com.example.demo01.domains.MiniCrm.Payment.service.PaymentProcessor;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.UnknownNullability;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContractPaymentProcessor implements PaymentProcessor {

    private final PaymentCycleService paymentCycleService;

    private final TransactionMapper transactionMapper;

    @Override
    public boolean support(@UnknownNullability TransactionRequest request) {
        return !request.getUsingId().isEmpty();
    }

    @Override
//    @Transactional
    public TransactionInfoDTO process(@UnknownNullability TransactionRequest request) {
        UpdatePaymentTermDetail updatePaymentTermDetail = paymentCycleService.handleAddPayment(
                request.getUsingId(),
                request.getAmount(),
                request.getExchangeRate()
        );


        List<PaymentCycleDTO> paymentCycleDTOS = updatePaymentTermDetail.getPaymentCycleDTOS();
        List<TransactionAllocate> allocates = updatePaymentTermDetail.getTransactionAllocates();
        request.setTransactionAllocates(allocates);
        request.setPaymentTermUpdate(paymentCycleDTOS);
        return transactionMapper.fromRequestToDto(request);
    }
}
