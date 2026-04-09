package com.example.demo01.domains.jpa.AssetManagement.Dimmensions.services;

import com.example.demo01.domains.jpa.AssetManagement.Dimmensions.dtos.MaintenanceCategory.MaintenanceCategoryInfo;
import com.example.demo01.domains.jpa.AssetManagement.Dimmensions.dtos.MaintenanceCategory.MaintenanceCategoryInfoWithItems;
import com.example.demo01.domains.jpa.AssetManagement.Dimmensions.dtos.MaintenanceCategory.MaintenanceCategoryRequest;
import com.example.demo01.domains.jpa.AssetManagement.Dimmensions.entities.MaintenanceCategoryEntity;

public interface MaintenanceCategoryService {

    MaintenanceCategoryInfo createMaintenanceCategory(MaintenanceCategoryRequest request);

    MaintenanceCategoryEntity getMaintenanceCategory(Long id);

    MaintenanceCategoryInfoWithItems getMaintenanceCategoryInfo(Long id);

}
