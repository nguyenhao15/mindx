package com.example.demo01.domains.mongo.MiniCrm.Contract.dtos.terminateCollection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TerminateRequest {

    @NotNull
    private String appendixCode;

    @NotNull
    private String customerId;

    @NotNull
    private String terminateReason;

    @NotNull
    private LocalDate updateDate;

    private List<TerminateAction> actions;

    private String updateType;

    @NotNull
    private String buId;
}
