package com.example.demo01.domains.mongo.MiniCrm.Contract.models;


import com.example.demo01.core.Aws3.dtos.FileResponseDTO;
import com.example.demo01.domains.mongo.MiniCrm.Contract.dtos.contracts.ContractConfigAction;
import com.example.demo01.domains.mongo.MiniCrm.Renting.dtos.RentingAction;
import com.example.demo01.utils.BaseEntity.Mongo.BusinessUnitEntity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "appendixes")
public class Appendix extends BusinessUnitEntity {

    @Id
    private String _id;

    private String agreementNumber;

    @Indexed(unique = true)
    private String appendixCode;

    @Indexed
    private String contractCode;

    @Indexed
    private String serviceId;

    @Indexed
    private String customerId;

    private ContractConfigAction configActions;

    private List<RentingAction> rentingActions;

    private String currencyCode;

    @Indexed
    private AppendixStatus appendixStatus;

    private Boolean invoiceCheck;

    private Double totalValue;

    private Double renewValue;

    private LocalDate activationDate;

    private LocalDate endDate;

    private FileResponseDTO fileResponseDTO;

    private String description;

    @Indexed
    private Boolean active;

}
