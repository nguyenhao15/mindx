package com.example.demo01.domains.jpa.AssetManagement.Maintenance.dtos.Maintenance;

import com.example.demo01.domains.jpa.AssetManagement.Utils.MaintenancesStatus;

import java.time.LocalDate;

public record MaintenanceSummaryDTO(
        Long id,
        String description,
        Long maintenanceItemId,
        Long maintenanceCategoryId,
        LocalDate issueDate,
        MaintenancesStatus maintenancesStatus,
        String locationId,
        Integer totalProposals,
        Double totalCost
) {
}
