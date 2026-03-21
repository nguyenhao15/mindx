package com.example.demo01.domains.MiniCrm.Payment.dtos.transaction;

import com.example.demo01.core.Aws3.dtos.FileResponseDTO;
import com.example.demo01.domains.MiniCrm.Contract.dtos.paymentCycles.PaymentCycleDTO;
import com.example.demo01.domains.MiniCrm.Invoice.dto.InvoiceInfo;
import com.example.demo01.domains.MiniCrm.Payment.models.TransactionAllocate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TransactionInfoDTO {

    private String _id;
    private String paymentOrderId;

    private Double exchangeRate;
    private Double amount;

    //    for Deposit or handed amount to allocation it
    private Double depositRemainAmount;
    private LocalDate paymentDate;

    private String customerId;
    private String buId;
    private String serviceId;

    private String customerName;
    private String buName;
    private String serviceName;

    //    Deposit, payment, handed
    private String paymentType;
    private String usingId;

    private String invoiceId;

    private InvoiceInfo invoiceInfo;

    private List<PaymentCycleDTO> paymentTermUpdate;

    private List<FileResponseDTO> attachments;

    private List<TransactionAllocate> transactionAllocates;

}
