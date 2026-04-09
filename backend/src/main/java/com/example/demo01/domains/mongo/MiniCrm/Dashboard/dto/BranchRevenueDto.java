// java
package com.example.demo01.domains.mongo.MiniCrm.Dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BranchRevenueDto {

    private String branchId;
    private String branchName;
    private double revenue;
}
