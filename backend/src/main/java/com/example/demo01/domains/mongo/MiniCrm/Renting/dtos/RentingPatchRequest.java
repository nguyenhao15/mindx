package com.example.demo01.domains.mongo.MiniCrm.Renting.dtos;

import com.example.demo01.domains.mongo.MiniCrm.Renting.model.RentingStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RentingPatchRequest {

    private String contractId;
    private String roomShortName;
    private String buShortName;
    private String serviceShortName;
    private String customerId;

    private LocalDate actualEndDate;

    private LocalDate assigned_from;
    private LocalDate assigned_to;

    private RentingStatus status;
}
