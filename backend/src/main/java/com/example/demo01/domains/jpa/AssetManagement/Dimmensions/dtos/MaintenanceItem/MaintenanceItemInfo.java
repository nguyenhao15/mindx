package com.example.demo01.domains.jpa.AssetManagement.Dimmensions.dtos.MaintenanceItem;

import com.example.demo01.domains.jpa.AssetManagement.Dimmensions.dtos.MaintenanceCategory.MaintenanceCategoryInfo;
import java.time.Instant;

public record MaintenanceItemInfo(
        Long id,

        String itemTitle,

        MaintenanceCategoryInfo maintenanceCategory,

        String createdBy,
        String lastModifiedBy,
        Instant lastModifiedDate,
        Instant createdDate,
        Long version
) {
}
