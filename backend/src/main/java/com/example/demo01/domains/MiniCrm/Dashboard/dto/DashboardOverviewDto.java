// java
package com.example.demo01.domains.MiniCrm.Dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DashboardOverviewDto {

    private BigDecimal rateWithLastMonth;
    private double totalRevenue;

    private double activeRoom;
    private double activeContract;

    private double KPI;
    private double growthRate;

    private double dueAmount;
    private double dueTicket;
    private double dueContract;
}
