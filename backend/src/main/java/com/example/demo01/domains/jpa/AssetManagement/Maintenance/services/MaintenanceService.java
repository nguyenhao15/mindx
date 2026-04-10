package com.example.demo01.domains.jpa.AssetManagement.Maintenance.services;

import com.example.demo01.domains.jpa.AssetManagement.Maintenance.dtos.Maintenance.MaintenanceRequestDto;
import com.example.demo01.domains.jpa.AssetManagement.Maintenance.dtos.Maintenance.MaintenanceSummaryDTO;
import com.example.demo01.domains.jpa.AssetManagement.Maintenance.entities.MaintenanceEntity;
import com.example.demo01.domains.jpa.AssetManagement.Utils.MaintenancesStatus;
import com.example.demo01.utils.BasePageResponse;
import com.example.demo01.utils.FilterWithPagination;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MaintenanceService {

    MaintenanceSummaryDTO createMaintenance(MaintenanceRequestDto requestDto);

    MaintenanceEntity getMaintenanceById(Long id);

    MaintenanceSummaryDTO getMaintenanceSummaryById(Long id);

    MaintenanceEntity getReference(Long maintenanceId);

    List<MaintenanceEntity> getAllMaintenances();

    BasePageResponse<MaintenanceSummaryDTO> getBasePageResponseWithFilter(FilterWithPagination filterWithPagination);

    BasePageResponse<MaintenanceSummaryDTO> buildPageResponse(Page<MaintenanceEntity> page);

    MaintenanceSummaryDTO updateMaintenance(Long id, MaintenanceRequestDto requestDto);

    String softDeleteMaintenance(Long maintenanceId);

    MaintenanceSummaryDTO upadteMaintenanceStatus(Long id, MaintenancesStatus status);

    String deleteMaintenanceById(Long id);
}
