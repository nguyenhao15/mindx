package com.example.demo01.domains.jpa.AssetManagement.Maintenance.services;

import com.example.demo01.domains.jpa.AssetManagement.Maintenance.dtos.Maintenance.*;
import com.example.demo01.domains.jpa.AssetManagement.Maintenance.entities.MaintenanceEntity;
import com.example.demo01.utils.BasePageResponse;
import com.example.demo01.utils.FilterWithPagination;
import com.example.demo01.utils.Query.PostgreSQL.ActionResponse;
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

    List<ActionResponse> getAvailableActions(Long id);

    BasePageResponse<MaintenanceSummaryDTO> getBasePageResponseWithFilter(FilterWithPagination filterWithPagination);

    BasePageResponse<MaintenanceSummaryDTO> buildPageResponse(Page<MaintenanceEntity> page);

    MaintenanceSummaryDTO updateMaintenance(Long id, MaintenanceUpdateRequest requestDto, List<MultipartFile> files);

    String softDeleteMaintenance(Long maintenanceId);

    String deleteMaintenanceById(Long id);
}
