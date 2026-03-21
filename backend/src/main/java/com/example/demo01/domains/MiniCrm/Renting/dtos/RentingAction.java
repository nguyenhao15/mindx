package com.example.demo01.domains.MiniCrm.Renting.dtos;

import com.example.demo01.domains.MiniCrm.Renting.model.RentingActionEnum;
import com.example.demo01.domains.MiniCrm.Renting.model.RentingStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RentingAction {

    private String _id;

    private RentingActionEnum actionType;

    private String contractId;

    private String roomShortName;

    private String buId;

    private String serviceShortName;

    private String customerId;

    private LocalDate assigned_from;

    private LocalDate assigned_to;

    private LocalDate actualEndDate;

    private RentingStatus status;

    private Boolean active;
}
