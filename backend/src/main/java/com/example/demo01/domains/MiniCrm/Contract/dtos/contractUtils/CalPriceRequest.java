package com.example.demo01.domains.MiniCrm.Contract.dtos.contractUtils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CalPriceRequest {

    private String productTag;
    private Double calculateRule;
    private Double usingNumber;
    private Double calculationPrice;
    private Double exchangeRate;
    private String unit;
}
