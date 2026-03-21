// java
package com.example.demo01.domains.MiniCrm.Dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TimeSeriesRevenue {
    // e.g. "2026-01-10" or "2026-01" or "2026"
    private String period;
    // "DAY", "MONTH", "YEAR" (or use an enum)
    private String granularity;
    private double revenue;
}
