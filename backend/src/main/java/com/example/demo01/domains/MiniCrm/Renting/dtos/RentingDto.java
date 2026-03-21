package com.example.demo01.domains.MiniCrm.Renting.dtos;

import com.example.demo01.domains.MiniCrm.Renting.model.RentingStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RentingDto {

    private String _id;

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

    private String serviceName;
    private String customerName;
    private String buName;
    private String roomName;

}
