package com.example.demo01.domains.jpa.AssetManagement.Dimmensions.dtos.MaintenanceCategory;

public record MaintenanceCategoryInfo(

        Long id,

        String categoryTitle,

        String description,

        Boolean hashChild
) {
}
