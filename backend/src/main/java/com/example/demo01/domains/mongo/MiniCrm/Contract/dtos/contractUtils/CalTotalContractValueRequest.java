package com.example.demo01.domains.mongo.MiniCrm.Contract.dtos.contractUtils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class CalTotalContractValueRequest {
    LocalDate startDate;
    LocalDate endDate;
    Double pricePerUnit;
    String unit;
}
