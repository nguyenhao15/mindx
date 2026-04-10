package com.example.demo01.domains.jpa.AssetManagement.Maintenance.services.Impl;

import com.example.demo01.core.Basement.dto.basement.BUInfoDto;
import com.example.demo01.core.Basement.service.BasementService;
import com.example.demo01.core.Exceptions.ResourceNotFoundException;
import com.example.demo01.domains.jpa.AssetManagement.Dimmensions.entities.MaintenanceCategoryEntity;
import com.example.demo01.domains.jpa.AssetManagement.Dimmensions.entities.MaintenanceItemEntity;
import com.example.demo01.domains.jpa.AssetManagement.Dimmensions.services.MaintenanceCategoryService;
import com.example.demo01.domains.jpa.AssetManagement.Dimmensions.services.MaintenanceItemService;
import com.example.demo01.domains.jpa.AssetManagement.Maintenance.dtos.Maintenance.MaintenanceRequestDto;
import com.example.demo01.domains.jpa.AssetManagement.Maintenance.dtos.Maintenance.MaintenanceSummaryDTO;
import com.example.demo01.domains.jpa.AssetManagement.Maintenance.entities.MaintenanceEntity;
import com.example.demo01.domains.jpa.AssetManagement.Maintenance.mapper.MaintenanceMapper;
import com.example.demo01.domains.jpa.AssetManagement.Maintenance.services.MaintenanceService;
import com.example.demo01.domains.jpa.AssetManagement.Utils.MaintenancesStatus;
import com.example.demo01.repository.postgreSQL.AssetManagement.MaintenanceRepository.MaintenanceRepository;
import com.example.demo01.utils.BasePageResponse;
import com.example.demo01.utils.FilterRequest;
import com.example.demo01.utils.FilterWithPagination;
import com.example.demo01.utils.PageInput;
import org.javers.repository.jql.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class MaintenanceServiceImpl implements MaintenanceService {

    @Autowired
    private MaintenanceRepository maintenanceRepository;

    @Autowired
    private MaintenanceItemService maintenanceItemService;

    @Autowired
    private MaintenanceCategoryService maintenanceCategoryService;

    @Autowired
    private MaintenanceMapper maintenanceMapper;

    @Autowired
    private BasementService  basementService;

    @Override
    public MaintenanceSummaryDTO createMaintenance(MaintenanceRequestDto requestDto) {
        Long categoryId = requestDto.getMaintenanceCategoryId();
        Long maintenanceItemId = requestDto.getMaintenanceItemId();
        String locationId = requestDto.getLocationId();

        MaintenanceCategoryEntity  categoryEntity = maintenanceCategoryService.getMaintenanceCategory(categoryId);
        MaintenanceItemEntity  itemEntity = maintenanceItemService.getMaintenanceItem(maintenanceItemId);
        BUInfoDto buInfoById = basementService.getBuInfoByShortName(locationId);

        MaintenanceEntity maintenanceEntity = maintenanceMapper.fromRequestToEntityMaintenance(requestDto);
        maintenanceEntity.setLocationId(buInfoById.getBuId());
        maintenanceEntity.setFixCategory(categoryEntity);
        maintenanceEntity.setFixItem(itemEntity);

        MaintenanceEntity maintenance = maintenanceRepository.save(maintenanceEntity);
        return maintenanceMapper.fromEntityToMaintenanceInfoDto(maintenance);
    }

    @Override
    public MaintenanceEntity getMaintenanceById(Long id) {
        return maintenanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Maintenance","id", id));
    }

    @Override
    public MaintenanceSummaryDTO getMaintenanceSummaryById(Long id) {
        MaintenanceEntity maintenanceEntity = getMaintenanceById(id);
        return maintenanceMapper.fromEntityToMaintenanceInfoDto(maintenanceEntity);
    }

    @Override
    public MaintenanceEntity getReference(Long maintenanceId) {
        getMaintenanceById(maintenanceId);
        return maintenanceRepository.getReferenceById(maintenanceId);
    }

    @Override
    public BasePageResponse<MaintenanceService> getBasePageResponseWithFilter(FilterWithPagination filterWithPagination) {
        PageInput pageInput = filterWithPagination.getPagination();
        List<FilterRequest> filters = filterWithPagination.getFilters();
        return null;
    }

    @Override
    public BasePageResponse<MaintenanceSummaryDTO> buildPageResponse(PageInput pageInput, List<FilterRequest> filterRequests) {

        return null;
    }

    @Override
    public MaintenanceSummaryDTO updateMaintenance(Long id, MaintenanceRequestDto requestDto) {
        MaintenanceEntity maintenanceEntity = getMaintenanceById(id);
        if (canTransition(maintenanceEntity.getMaintenancesStatus(), requestDto.getMaintenancesStatus())) {
            maintenanceEntity.setMaintenancesStatus(requestDto.getMaintenancesStatus());
        } else if (requestDto.getMaintenancesStatus() != null && !maintenanceEntity.getMaintenancesStatus().equals(requestDto.getMaintenancesStatus())) {
            throw new IllegalStateException("Invalid status transition from " + maintenanceEntity.getMaintenancesStatus() + " to " + requestDto.getMaintenancesStatus());
        }
        maintenanceMapper.updateEntityFromRequest(requestDto, maintenanceEntity);
        maintenanceRepository.save(maintenanceEntity);
        return maintenanceMapper.fromEntityToMaintenanceInfoDto(maintenanceEntity);
    }

    @Override
    public MaintenanceSummaryDTO softDeleteMaintenance(Long maintenanceId) {
        MaintenanceEntity maintenanceEntity = getMaintenanceById(maintenanceId);
        maintenanceEntity.setIsDeleted(true);
        MaintenanceEntity maintenance = maintenanceRepository.save(maintenanceEntity);
        return maintenanceMapper.fromEntityToMaintenanceInfoDto(maintenance);
    }

    @Override
    public MaintenanceSummaryDTO upadteMaintenanceStatus(Long id, MaintenancesStatus status) {
        MaintenanceEntity maintenanceEntity = getMaintenanceById(id);
        if (status == null || maintenanceEntity.getMaintenancesStatus() == null ) return null;
        if (!canTransition(maintenanceEntity.getMaintenancesStatus(), status)) {
            throw new IllegalStateException("Invalid status transition from " + maintenanceEntity.getMaintenancesStatus() + " to " + status);
        }
        if (maintenanceEntity.getMaintenancesStatus().toString().equals("FINISHED") && status.equals(MaintenancesStatus.WAITING)) {
            maintenanceEntity.setReWork(true);
        }
        maintenanceEntity.setMaintenancesStatus(status);
        MaintenanceEntity maintenance = maintenanceRepository.save(maintenanceEntity);
        return maintenanceMapper.fromEntityToMaintenanceInfoDto(maintenance);
    }

    @Override
    public String deleteMaintenanceById(Long id) {
        MaintenanceEntity maintenanceEntity = getMaintenanceById(id);
        maintenanceRepository.delete(maintenanceEntity);
        return "Deleted successfully";
    }

    private static final Map<MaintenancesStatus, List<MaintenancesStatus>> ALLOWED_TRANSITIONS = new EnumMap<>(MaintenancesStatus.class);

    static {
        ALLOWED_TRANSITIONS.put(MaintenancesStatus.WAITING, List.of(MaintenancesStatus.APPROVED, MaintenancesStatus.REJECTED ));
        ALLOWED_TRANSITIONS.put(MaintenancesStatus.REJECTED, List.of(MaintenancesStatus.WAITING ));
        ALLOWED_TRANSITIONS.put(MaintenancesStatus.APPROVED, List.of(MaintenancesStatus.REJECTED, MaintenancesStatus.CHECKED, MaintenancesStatus.PROCESSING, MaintenancesStatus.FINISHED ));
        ALLOWED_TRANSITIONS.put(MaintenancesStatus.CHECKED, List.of(MaintenancesStatus.REJECTED, MaintenancesStatus.PROCESSING,  MaintenancesStatus.FINISHED ));
        ALLOWED_TRANSITIONS.put(MaintenancesStatus.FINISHED, List.of(MaintenancesStatus.WAITING, MaintenancesStatus.COMPLETED ));
        ALLOWED_TRANSITIONS.put(MaintenancesStatus.COMPLETED, List.of());
    }

    private boolean canTransition(MaintenancesStatus currentStatus, MaintenancesStatus targetStatus) {
        if (currentStatus == null || targetStatus == null) return false;
        return ALLOWED_TRANSITIONS.get(currentStatus).contains(targetStatus);
    }

}
