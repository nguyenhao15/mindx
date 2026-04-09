package com.example.demo01.domains.mongo.MiniCrm.Contract.dtos.appendix;


import com.example.demo01.core.Aws3.dtos.FileResponseDTO;
import com.example.demo01.domains.mongo.MiniCrm.Contract.dtos.contracts.ContractConfigAction;
import com.example.demo01.domains.mongo.MiniCrm.Contract.models.AppendixStatus;
import com.example.demo01.domains.mongo.MiniCrm.Renting.dtos.RentingAction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppendixInfoDto {
    private String _id;

    private String agreementNumber;
    private String appendixCode;
    private String appendixTag;

    private String contractCode;

    private String serviceId;
    private String customerId;
    private String buId;

    private ContractConfigAction configActions;

    private List<RentingAction> rentingActions;

    private String serviceName;
    private String customerName;
    private String buName;

    private String currencyCode;
    private Double totalValue;
    private Double renewValue;

    private AppendixStatus appendixStatus;

    private Boolean invoiceCheck;

    private LocalDate activationDate;

    private LocalDate endDate;

    private FileResponseDTO fileResponseDTO;

    private String description;

    private Boolean active;
    private Instant createdDate;
    private Instant lastModifiedDate;
    private String createdBy;
    private String lastModifiedBy;
}
