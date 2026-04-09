package com.example.demo01.domains.jpa.AssetManagement.Dimmensions.dtos.MaintenanceCategory;

import com.example.demo01.domains.jpa.AssetManagement.Dimmensions.dtos.MaintenanceItem.MaintenanceItemInfoDto;

import java.time.Instant;
import java.util.List;

public record MaintenanceCategoryInfoWithItems (
        Long id,

        String categoryTitle,

        String description,

        Boolean hashChild,

        List<MaintenanceItemInfoDto> maintenanceItems,

        String createdBy,

        String lastModifiedBy,

        Instant lastModifiedDate,

        Instant createdDate
) {}
