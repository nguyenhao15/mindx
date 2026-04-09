package com.example.demo01.domains.jpa.AssetManagement.Maintenance.services;

import com.example.demo01.domains.jpa.AssetManagement.Maintenance.dtos.Maintenance.MaintenanceInfoDto;
import com.example.demo01.domains.jpa.AssetManagement.Maintenance.dtos.Maintenance.MaintenanceRequestDto;
import com.example.demo01.domains.jpa.AssetManagement.Maintenance.dtos.Maintenance.MaintenanceSummaryDTO;

public interface MaintenanceService {

    MaintenanceSummaryDTO createMaintenance(MaintenanceRequestDto requestDto);

}
