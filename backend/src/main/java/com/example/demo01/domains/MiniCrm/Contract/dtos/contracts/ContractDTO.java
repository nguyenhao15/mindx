package com.example.demo01.domains.MiniCrm.Contract.dtos.contracts;

import com.example.demo01.core.Aws3.dtos.FileResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ContractDTO {

    private String _id;

    private String contractCode;

    private String customerName;
    private String basementName;
    private String serviceName;

    private String buId;
    private String serviceId;
    private String customerId;

    private Double contractValue;

    private FileResponseDTO fileResponseDTO;

    private LocalDate startDate;
    private LocalDate endDate;
    private String contractStatus;

}
