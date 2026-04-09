package com.example.demo01.domains.jpa.AssetManagement.Maintenance.dtos.Maintenance;

import com.example.demo01.domains.jpa.AssetManagement.Maintenance.dtos.MaintenancesProposals.ProposalInfoDto;
import com.example.demo01.domains.jpa.AssetManagement.Utils.MaintenancesStatus;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

public record MaintenanceInfoDto (
        Long id,

        String description,

        LocalDate issueDate,

        MaintenancesStatus maintenancesStatus,

        List<ProposalInfoDto> maintenancesProposals,

        String createdBy,
        String lastModifiedBy,
        Instant createdDate,
        Instant lastModifiedDate
) {}