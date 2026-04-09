package com.example.demo01.domains.mongo.MiniCrm.ProfitAndLost.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Configuration
public class AllocationObject {

    private LocalDate beginAllocationDate;
    private Integer totalUsingDays;

    private Double allocationTotalAmount;
    private Double allocationPerLoop;

    private Double totalPaidAmount;

    private Double exchangeRate;

    private String buId;
    private String serviceId;

    private String usingId;
    private String invoiceId;
    private String paymentCycleId;
}
