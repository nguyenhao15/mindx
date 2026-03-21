package com.example.demo01.domains.MiniCrm.Contract.dtos.appendix;

import com.example.demo01.core.Aws3.dtos.FileResponseDTO;
import com.example.demo01.domains.MiniCrm.Contract.dtos.contracts.ContractConfigAction;
import com.example.demo01.domains.MiniCrm.Contract.models.AppendixStatus;
import com.example.demo01.domains.MiniCrm.Renting.dtos.RentingAction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppendixRequest {

    private String agreementNumber;

    private String contractCode;
    private String appendixCode;
    private String currencyCode;
    private Double totalValue;
    private Double renewValue;

    private AppendixStatus appendixStatus;

    private String serviceId;
    private String customerId;
    private String buId;

    private List<String> roomId;
    private String appendixTag;

    private ContractConfigAction configActions;

    private List<RentingAction> rentingActions;

    private LocalDate activationDate;
    private LocalDate endDate;

    private FileResponseDTO fileResponseDTO;

    private String description;
    private Boolean invoiceCheck;

    private Boolean active = false;

    private int paymentTerms;
    private Double monthlyRenting;

    private String updateAppendix;
    private Double paidAmount = 0.0;
}
