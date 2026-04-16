package com.example.demo01.domains.jpa.AssetManagement.Maintenance.services;

import com.example.demo01.domains.jpa.AssetManagement.Maintenance.dtos.Maintenance.MaintenanceDetailResponse;
import com.example.demo01.domains.jpa.AssetManagement.Maintenance.dtos.Maintenance.MaintenanceRequestDto;
import com.example.demo01.domains.jpa.AssetManagement.Maintenance.dtos.Maintenance.MaintenanceSummaryDTO;
import com.example.demo01.domains.jpa.AssetManagement.Maintenance.dtos.Maintenance.MaintenanceUpdateRequest;
import com.example.demo01.domains.jpa.AssetManagement.Maintenance.entities.MaintenanceEntity;
import com.example.demo01.domains.jpa.AssetManagement.Utils.MaintenancesStatus;
import com.example.demo01.utils.BasePageResponse;
import com.example.demo01.utils.FilterWithPagination;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MaintenanceService {

    MaintenanceSummaryDTO createMaintenance(MaintenanceRequestDto requestDto, List<MultipartFile> files);

    MaintenanceEntity getMaintenanceById(Long id);

    MaintenanceSummaryDTO getMaintenanceSummaryById(Long id);

    MaintenanceEntity getReference(Long maintenanceId);

    MaintenanceDetailResponse getMaintenanceDetailsInfo(Long maintenanceId);

    List<MaintenanceEntity> getAllMaintenances();

    BasePageResponse<MaintenanceSummaryDTO> getBasePageResponseWithFilter(FilterWithPagination filterWithPagination);

    BasePageResponse<MaintenanceSummaryDTO> buildPageResponse(Page<MaintenanceEntity> page);

    MaintenanceSummaryDTO updateMaintenance(Long id, MaintenanceUpdateRequest requestDto);

    String softDeleteMaintenance(Long maintenanceId);

    MaintenanceSummaryDTO updateMaintenanceStatus(Long id, MaintenancesStatus status);

    String deleteMaintenanceById(Long id);
}
