package com.example.demo01.domains.jpa.AssetManagement.Maintenance.dtos.Maintenance;

import com.example.demo01.domains.jpa.AssetManagement.Dimmensions.dtos.MaintenanceCategory.MaintenanceCategoryNestInfo;
import com.example.demo01.domains.jpa.AssetManagement.Dimmensions.dtos.MaintenanceItem.MaintenanceItemInfoDto;
import com.example.demo01.domains.jpa.AssetManagement.Utils.MaintenancesStatus;

import java.time.Instant;
import java.time.LocalDate;

public record MaintenanceSummaryDTO (
        Long id,

        String description,

        LocalDate issueDate,

        MaintenancesStatus maintenancesStatus,

        MaintenanceCategoryNestInfo fixCategory,

        MaintenanceItemInfoDto fixItem,

        String locationId,

        String locationName,

        Boolean reWork,

        Integer totalProposals,

        Double totalCost,

        LocalDate inspectAt,

        String assignedTo,

        LocalDate verifiedAt,

        String createdBy,

        String lastModifiedBy,

        Instant createdDate,

        Instant lastModifiedDate
) {
}
