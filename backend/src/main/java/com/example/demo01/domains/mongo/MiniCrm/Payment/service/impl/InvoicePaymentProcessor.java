package com.example.demo01.domains.mongo.MiniCrm.Payment.service.impl;

import com.example.demo01.domains.mongo.MiniCrm.Invoice.dto.InvoiceInfo;
import com.example.demo01.domains.mongo.MiniCrm.Invoice.service.InvoiceService;
import com.example.demo01.domains.mongo.MiniCrm.Payment.dtos.transaction.TransactionInfoDTO;
import com.example.demo01.domains.mongo.MiniCrm.Payment.dtos.transaction.TransactionRequest;
import com.example.demo01.domains.mongo.MiniCrm.Payment.mapper.TransactionMapper;
import com.example.demo01.domains.mongo.MiniCrm.Payment.models.TransactionAllocate;
import com.example.demo01.domains.mongo.MiniCrm.Payment.service.PaymentProcessor;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.UnknownNullability;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InvoicePaymentProcessor implements PaymentProcessor {

    private final InvoiceService invoiceService;

    private final TransactionMapper transactionMapper;

    @Override
    public boolean support(@UnknownNullability TransactionRequest request) {
        return request.getUsingId().isEmpty();
    }

    @Override
    public TransactionInfoDTO process(@UnknownNullability TransactionRequest request) {
        InvoiceInfo invoiceInfo = invoiceService.invoiceByPo(request);
        List<TransactionAllocate> allocates = new ArrayList<>();
        TransactionAllocate transactionAllocate = new TransactionAllocate();
        transactionAllocate.setIdentifier(invoiceInfo.get_id());
        transactionAllocate.setAllocateItem("Invoice");
        transactionAllocate.setAllocateAmount(request.getAmount());
        allocates.add(transactionAllocate);

        request.setTransactionAllocates(allocates);

        TransactionInfoDTO transactionInfoDTO = transactionMapper.fromRequestToDto(request);
        transactionInfoDTO.setInvoiceInfo(invoiceInfo);
        transactionInfoDTO.setInvoiceId(invoiceInfo.getInvoiceId());

        return transactionInfoDTO;
    }
}
