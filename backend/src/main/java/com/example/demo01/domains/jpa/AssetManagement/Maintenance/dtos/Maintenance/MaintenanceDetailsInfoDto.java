package com.example.demo01.domains.jpa.AssetManagement.Maintenance.dtos.Maintenance;

import com.example.demo01.domains.jpa.AssetManagement.Dimmensions.dtos.MaintenanceCategory.MaintenanceCategoryNestInfo;
import com.example.demo01.domains.jpa.AssetManagement.Dimmensions.dtos.MaintenanceItem.MaintenanceItemInfoDto;
import com.example.demo01.domains.jpa.AssetManagement.Maintenance.dtos.MaintenancesProposals.MaintenancesProposalsDto;
import com.example.demo01.domains.jpa.AssetManagement.Utils.MaintenancesStatus;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

public record MaintenanceDetailsInfoDto(
        Long id,

        String description,

        LocalDate issueDate,

        String maintenancesStatus,

        LocalDate completionAt,

        LocalDate verifiedAt,

        LocalDate inspectAt,

        String assignedTo,

        String maintenanceType,

        MaintenanceCategoryNestInfo fixCategory,

        MaintenanceItemInfoDto fixItem,

        String locationId,

        String locationName,

        Integer totalProposals,

        Double totalCost,

        Boolean reWork,

        List<MaintenancesProposalsDto> maintenancesProposals,

        String createdBy,

        String lastModifiedBy,

        Instant createdDate,

        Instant lastModifiedDate


) {}