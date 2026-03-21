package com.example.demo01.domains.MiniCrm.Payment.dtos.transaction;

import com.example.demo01.domains.MiniCrm.Contract.dtos.paymentCycles.PaymentCycleDTO;
import com.example.demo01.domains.MiniCrm.Invoice.dto.InvoiceRequest;
import com.example.demo01.domains.MiniCrm.Payment.models.TransactionAllocate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TransactionRequest {

    private String paymentOrderId;
    private Double amount;
    private Double exchangeRate;
    private Double localAmount;

    //    for Deposit or handed amount to allocation it
    private Double depositRemainAmount;
    private LocalDate paymentDate;
    private String customerId;

    private String buId;
    private String serviceId;
    private String usingId;
    private String invoiceId;

    //    Deposit, payment, handed
    private String paymentType;

    private InvoiceRequest invoiceInfo;

    private List<PaymentCycleDTO> paymentTermUpdate;

    private List<TransactionAllocate> transactionAllocates;
}
