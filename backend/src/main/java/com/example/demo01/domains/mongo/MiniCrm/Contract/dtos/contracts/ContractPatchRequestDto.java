package com.example.demo01.domains.mongo.MiniCrm.Contract.dtos.contracts;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class ContractPatchRequestDto {

    private String contractCode;
    private String buId;
    private String serviceId;

    //    For MKT to check value per contract

    private String customerId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String contractStatus;
}
