package com.example.demo01.domains.jpa.AssetManagement.Maintenance.services;

import com.example.demo01.domains.jpa.AssetManagement.Maintenance.dtos.Maintenance.MaintenanceRequestDto;
import com.example.demo01.domains.jpa.AssetManagement.Maintenance.dtos.Maintenance.MaintenanceSummaryDTO;
import com.example.demo01.domains.jpa.AssetManagement.Maintenance.entities.MaintenanceEntity;

public interface MaintenanceService {

    MaintenanceSummaryDTO createMaintenance(MaintenanceRequestDto requestDto);

    MaintenanceEntity getMaintenanceById(Long id);

    MaintenanceSummaryDTO getMaintenanceSummaryById(Long id);

    MaintenanceSummaryDTO updateMaintenance(Long id, MaintenanceRequestDto requestDto);

    String deleteMaintenanceById(Long id);
}
