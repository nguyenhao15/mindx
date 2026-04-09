package com.example.demo01.domains.jpa.AssetManagement.Dimmensions.services;

import com.example.demo01.domains.jpa.AssetManagement.Dimmensions.dtos.MaintenanceCategory.MaintenanceCategoryInfo;
import com.example.demo01.domains.jpa.AssetManagement.Dimmensions.dtos.MaintenanceCategory.MaintenanceCategoryRequest;

public interface MaintenanceCategoryService {

    MaintenanceCategoryInfo createMaintenanceCategory(MaintenanceCategoryRequest request);

}
