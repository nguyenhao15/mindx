package com.example.demo01.domains.mongo.MiniCrm.Payment.dtos.transaction;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TransactionPatchRequest {

    private String paymentOrderId;

    private Double amount;

    //    for Deposit or handed amount to allocation it
    private Double depositRemainAmount;
    private LocalDate paymentDate;
    private String customerId;

    private String buId;
    private String serviceId;

    private String invoiceId;

    //    Deposit, payment, handed
    private String paymentType;
    private String usingId;

}
