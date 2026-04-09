package com.example.demo01.domains.mongo.MiniCrm.Contract.dtos.paymentCycles;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Configuration
public class PaymentCycleBulkGen {

    private LocalDate startDate;
    private LocalDate endDate;

    private int paymentTerm;

    private Double valuePerMonth;
    private String appendixId;

    private Double totalValue;

    private Double paidAmount;
    private String currencyId;

    private String serviceId;
    private String buId;
    private String contractCode;
    private String customerId;

    private Boolean invoiceCheck;
    private Boolean exportedInvoice;

    private Boolean isManualMode = false;
}
