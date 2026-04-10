package com.example.demo01.domains.jpa.AssetManagement.Maintenance.services;

import com.example.demo01.domains.jpa.AssetManagement.Maintenance.dtos.Maintenance.MaintenanceRequestDto;
import com.example.demo01.domains.jpa.AssetManagement.Maintenance.dtos.Maintenance.MaintenanceSummaryDTO;
import com.example.demo01.domains.jpa.AssetManagement.Maintenance.entities.MaintenanceEntity;
import com.example.demo01.domains.jpa.AssetManagement.Utils.MaintenancesStatus;

public interface MaintenanceService {

    MaintenanceSummaryDTO createMaintenance(MaintenanceRequestDto requestDto);

    MaintenanceEntity getMaintenanceById(Long id);

    MaintenanceSummaryDTO getMaintenanceSummaryById(Long id);

    MaintenanceEntity getReference(Long maintenanceId);

    MaintenanceSummaryDTO updateMaintenance(Long id, MaintenanceRequestDto requestDto);

    MaintenanceSummaryDTO upadteMaintenanceStatus(Long id, MaintenancesStatus status);

    String deleteMaintenanceById(Long id);
}
