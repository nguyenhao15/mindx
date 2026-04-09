package com.example.demo01.domains.mongo.MiniCrm.Payment.dtos.transaction;

import com.example.demo01.domains.mongo.MiniCrm.Contract.dtos.paymentCycles.PaymentCycleDTO;
import com.example.demo01.domains.mongo.MiniCrm.Payment.models.TransactionAllocate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdatePaymentTermDetail {
    List<PaymentCycleDTO> paymentCycleDTOS;
    List<TransactionAllocate> transactionAllocates;
}
