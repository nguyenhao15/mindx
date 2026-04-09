package com.example.demo01.domains.mongo.MiniCrm.Contract.dtos.paymentCycles;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentCycleDTO {

    private String _id;
    private String cycleId;
    private String contractId;
    private String appendixId;


    private String buId;
    private String serviceId;

    private String customerName;
    private String basementName;
    private String serviceName;

    private String customerId;
    private String currencyId;

    private String status;
    private Boolean active;

    private Boolean exportedInvoice;
    //    days field for pnl calculation stage
    private Integer days;
    private LocalDate cycleDueDate;

    private Double monthlyAmount;
    private Double totalAmount;
    private Double paidAmount;
    private Double remainAmount;
}
