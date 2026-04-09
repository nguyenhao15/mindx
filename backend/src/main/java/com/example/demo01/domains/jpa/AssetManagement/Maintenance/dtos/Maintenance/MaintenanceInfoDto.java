package com.example.demo01.domains.jpa.AssetManagement.Maintenance.dtos.Maintenance;

import com.example.demo01.domains.jpa.AssetManagement.Dimmensions.dtos.MaintenanceCategory.MaintenanceCategoryNestInfo;
import com.example.demo01.domains.jpa.AssetManagement.Dimmensions.dtos.MaintenanceItem.MaintenanceItemInfoDto;
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

        MaintenanceCategoryNestInfo fixCategory,

        MaintenanceItemInfoDto fixItem,

        String locationId,

        Integer totalProposals,

        Double totalCost,

        List<ProposalInfoDto> maintenancesProposals,

        String createdBy,

        String lastModifiedBy,

        Instant createdDate,

        Instant lastModifiedDate


) {}