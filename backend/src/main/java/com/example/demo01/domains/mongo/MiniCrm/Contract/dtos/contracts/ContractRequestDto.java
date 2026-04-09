package com.example.demo01.domains.mongo.MiniCrm.Contract.dtos.contracts;

import com.example.demo01.core.Aws3.dtos.FileResponseDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ContractRequestDto {

    private String contractCode;

    private String updateType;

    @NotBlank(message = "buShortName must not blank")
    private String buId;

    private String serviceId;

    private FileResponseDTO contractPath;

    private String customerId;

    private LocalDate startDate;

    @Indexed
    private LocalDate endDate;

    private String contractStatus;
}
