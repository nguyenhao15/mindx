package com.example.demo01.domains.MiniCrm.Contract.dtos.contracts;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ContractConfigAction {

    private String configType;
    private String previousConfigId;

    private Double updatePaidAmount;

    private String serviceId;
    private Double monthlyValue;
    private String customerId;
}
