package com.example.demo01.domains.jpa.AssetManagement.Dimmensions.dtos.MaintenanceCategory;

import com.example.demo01.domains.jpa.AssetManagement.Dimmensions.dtos.MaintenanceItem.MaintenanceItemInfo;

import java.time.Instant;
import java.util.List;

public record MaintenanceCategoryInfo(
        Long id,
        String categoryTitle,
        Boolean hashChild,
        List<MaintenanceItemInfo> maintenanceItems,

        String createdBy,
        String lastModifiedBy,
        Instant lastModifiedDate,
        Instant createdDate,
        Long version
) {
}
