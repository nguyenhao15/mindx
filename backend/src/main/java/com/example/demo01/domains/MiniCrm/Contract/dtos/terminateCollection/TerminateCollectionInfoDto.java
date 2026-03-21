package com.example.demo01.domains.MiniCrm.Contract.dtos.terminateCollection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class TerminateCollectionInfoDto {

    private String _id;

    private String appendixCode;

    private String customerId;

    private String terminateReason;

    private String updateType;

    private List<TerminateAction> actions;

    private LocalDate updateDate;

    private String buId;

    private String createdBy;
    private String lastModifiedBy;
    private Instant createdDate;
    private Instant lastModifiedDate;
}
