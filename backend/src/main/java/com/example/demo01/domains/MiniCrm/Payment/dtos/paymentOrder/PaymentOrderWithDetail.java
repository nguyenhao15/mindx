package com.example.demo01.domains.MiniCrm.Payment.dtos.paymentOrder;

import com.example.demo01.domains.MiniCrm.Contract.dtos.paymentCycles.PaymentCycleDTO;
import com.example.demo01.domains.MiniCrm.Invoice.dto.InvoiceInfo;
import com.example.demo01.domains.MiniCrm.Payment.dtos.transaction.TransactionInfoDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentOrderWithDetail {

    PaymentOrderDTO paymentOrderDTO;
    List<TransactionInfoDTO> transactionInfoDTOS;
    List<PaymentCycleDTO> paymentTermUpdates;
    List<InvoiceInfo> invoiceInfos;

}
