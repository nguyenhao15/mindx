package com.example.demo01.domains.mongo.MiniCrm.Invoice.dto;

import com.example.demo01.domains.mongo.MiniCrm.Contract.dtos.appendix.AppendixInfoDto;
import com.example.demo01.domains.mongo.MiniCrm.Contract.dtos.paymentCycles.PaymentCycleDTO;
import com.example.demo01.core.SpaceCustomer.models.CustomerInfo;
import com.example.demo01.domains.mongo.MiniCrm.Payment.dtos.transaction.TransactionInfoDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class InvoiceProcessInfoDto {
    private InvoiceInfo invoiceInfo;
    private CustomerInfo customerInfo;
    private List<TransactionInfoDTO> transactions;
    private AppendixInfoDto appendixInfo;
    private PaymentCycleDTO paymentTermInfo;
}
