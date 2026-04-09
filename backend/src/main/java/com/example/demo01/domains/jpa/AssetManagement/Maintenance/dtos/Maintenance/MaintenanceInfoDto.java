package com.example.demo01.domains.jpa.AssetManagement.Maintenance.dtos.Maintenance;


import com.example.demo01.domains.jpa.AssetManagement.Dimmensions.entities.MaintenanceCategoryEntity;
import com.example.demo01.domains.jpa.AssetManagement.Dimmensions.entities.MaintenanceItemEntity;
import com.example.demo01.domains.jpa.AssetManagement.Utils.MaintenancesStatus;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

public record MaintenanceInfoDto (
        Long id,

        String description,

        MaintenanceCategoryEntity fixCategory,
        MaintenanceItemEntity fixItem,

        LocalDate issueDate,

        MaintenancesStatus maintenancesStatus,

        List<MaintenanceInfoDto> maintenancesProposals,

        String createdBy,
        String lastModifiedBy,
        Instant createdDate,
        Instant lastModifiedDate
) {}