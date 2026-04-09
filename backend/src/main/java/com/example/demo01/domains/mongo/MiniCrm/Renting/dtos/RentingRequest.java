package com.example.demo01.domains.mongo.MiniCrm.Renting.dtos;

import com.example.demo01.domains.mongo.MiniCrm.Renting.model.RentingStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RentingRequest {

    private String _id;
    private String actionType; // e.g., "RENEW", "TERMINATE"

    private String contractId;
    private String roomShortName;
    private String buId;
    private String serviceShortName;
    private String customerId;
    private LocalDate assigned_from;
    private LocalDate assigned_to;

    private LocalDate actualEndDate;

    private RentingStatus status = RentingStatus.PENDING;
    private Boolean active = true;

}
