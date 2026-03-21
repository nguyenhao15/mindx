package com.example.demo01.domains.MiniCrm.Contract.dtos.paymentCycles;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentCyclesDtoRequest {

    private String cycleId;

    private String contractId;
    private String appendixId;


    private String buId;
    private String serviceId;
    private String status;

    private Integer days;
    private LocalDate cycleDueDate;
    private String customerId;

    private Boolean exportedInvoice;

    private Double monthlyAmount;
    private Double totalAmount;
    private Double paidAmount;
    private Double remainAmount;
}
