package com.example.demo01.domains.jpa.AssetManagement.Maintenance.services.Impl;

import com.example.demo01.core.Attachment.dto.AttachmentDto;
import com.example.demo01.core.Attachment.service.AttachmentService;
import com.example.demo01.core.Basement.dto.basement.BUInfoDto;
import com.example.demo01.core.Basement.service.BasementService;
import com.example.demo01.core.Exceptions.ResourceNotFoundException;
import com.example.demo01.domains.jpa.AssetManagement.Dimmensions.entities.MaintenanceCategoryEntity;
import com.example.demo01.domains.jpa.AssetManagement.Dimmensions.entities.MaintenanceItemEntity;
import com.example.demo01.domains.jpa.AssetManagement.Dimmensions.services.MaintenanceCategoryService;
import com.example.demo01.domains.jpa.AssetManagement.Dimmensions.services.MaintenanceItemService;
import com.example.demo01.domains.jpa.AssetManagement.Maintenance.dtos.Maintenance.*;
import com.example.demo01.domains.jpa.AssetManagement.Maintenance.entities.MaintenanceEntity;
import com.example.demo01.domains.jpa.AssetManagement.Maintenance.mapper.MaintenanceMapper;
import com.example.demo01.domains.jpa.AssetManagement.Maintenance.services.MaintenanceService;
import com.example.demo01.domains.jpa.AssetManagement.Maintenance.utils.MaintenanceQueryUtil;
import com.example.demo01.domains.jpa.Core.Audit.dto.AuditUpdateDto;
import com.example.demo01.domains.jpa.Core.Audit.dto.AuditUpdateRequest;
import com.example.demo01.domains.jpa.Core.Audit.service.AuditUpdateService;
import com.example.demo01.repository.postgreSQL.AssetManagement.MaintenanceRepository.MaintenanceRepository;
import com.example.demo01.utils.*;
import com.example.demo01.utils.Query.PostgreSQL.*;
import org.jetbrains.annotations.UnknownNullability;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
public class MaintenanceServiceImpl implements MaintenanceService {

    @Autowired
    private MaintenanceRepository maintenanceRepository;

    @Autowired
    private MaintenanceItemService maintenanceItemService;

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private MaintenanceCategoryService maintenanceCategoryService;

    @Autowired
    private AuditUpdateService  auditUpdateService;

    @Autowired
    private DynamicSpecificationBuilder<MaintenanceEntity> dynamicSpecificationBuilder;

    @Autowired
    private MaintenanceQueryUtil  maintenanceQueryUtil;

    @Autowired
    private PostgreSQLPageUtil postgreSQLPageUtil;

    @Autowired
    private MaintenanceMapper maintenanceMapper;

    @Autowired
    private BasementService  basementService;

    @Autowired
    private ApprovalEngineUtil  approvalEngineUtil;

    @Override
    public MaintenanceSummaryDTO createMaintenance(MaintenanceRequestDto requestDto, List<MultipartFile> files) {
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

        String identify = ModuleEnum.MAINTENANCE +"-"+ maintenance.getId();
        attachmentService.addAttachment(files,identify, "maintenance", ModuleEnum.MAINTENANCE, false);

        Map<String, Object> lookupMap = basementService.getBatchBuFullNames(List.of(locationId));
        return maintenanceMapper.fromEntityToMaintenanceInfoDto(maintenance,lookupMap);
    }

    @Override
    public MaintenanceEntity getMaintenanceById(Long id) {
        return maintenanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Maintenance","id", id));
    }

    @Override
    public MaintenanceSummaryDTO getMaintenanceSummaryById(Long id) {
        MaintenanceEntity maintenanceEntity = getMaintenanceById(id);
        Map<String,Object> lookupMap = basementService.getBatchBuFullNames(List.of(maintenanceEntity.getLocationId()));
        return maintenanceMapper.fromEntityToMaintenanceInfoDto(maintenanceEntity,lookupMap);
    }

    @Override
    public MaintenanceEntity getReference(Long maintenanceId) {
        getMaintenanceById(maintenanceId);
        return maintenanceRepository.getReferenceById(maintenanceId);
    }

    @Override
    public MaintenanceDetailResponse getMaintenanceDetailsInfo(Long maintenanceId) {
        MaintenanceDetailResponse maintenanceDetailResponse = new MaintenanceDetailResponse();
        MaintenanceEntity maintenanceEntity = getMaintenanceById(maintenanceId);
        Map<String,Object> lookupMap = basementService.getBatchBuFullNames(List.of(maintenanceEntity.getLocationId()));
        MaintenanceDetailsInfoDto detailsInfoDto = maintenanceMapper.fromEntityToMaintenanceDetailsInfoDto(maintenanceEntity, lookupMap);

        List<AuditUpdateDto> auditUpdate = auditUpdateService.getAuditUpdatesByEntityName(ModuleEnum.MAINTENANCE, maintenanceId);
        maintenanceDetailResponse.setMaintenanceDetailsInfo(detailsInfoDto);
        maintenanceDetailResponse.setUpdateHistory(auditUpdate);
        List<AttachmentDto> attachmentDtos  = attachmentService.getAttachmentsWithPreUrl(ModuleEnum.MAINTENANCE + "-"+ maintenanceId);
        maintenanceDetailResponse.setFiles(attachmentDtos);
        return maintenanceDetailResponse;
    }

    @Override
    public List<MaintenanceEntity> getAllMaintenances() {
        return maintenanceRepository.findAll();
    }

    @Override
    public List<ActionResponse> getAvailableActions(Long id) {
        MaintenanceEntity maintenanceEntity = getMaintenanceById(id);
        return approvalEngineUtil.getAvailableAction(maintenanceEntity.getMaintenancesStatus(), "*", maintenanceEntity.getCreatedBy(), ModuleEnum.MAINTENANCE);
    }

    @Override
    public BasePageResponse<MaintenanceSummaryDTO> getBasePageResponseWithFilter(FilterWithPagination filterWithPagination) {
        PageInput pageInput = filterWithPagination.getPagination();
        Specification<MaintenanceEntity> finalSpecification = maintenanceQueryUtil.buildSpecification(filterWithPagination);
        Page<MaintenanceEntity> page = postgreSQLPageUtil.buildPageResponse(
                finalSpecification,
                pageInput,
                maintenanceRepository
        );

        return buildPageResponse(page);
    }

    @Override
    public BasePageResponse<MaintenanceSummaryDTO> buildPageResponse(Page<MaintenanceEntity> page) {
        List<MaintenanceEntity> maintenanceEntities = page.getContent();

        List<String> locationIds = maintenanceEntities.stream()
                .map(MaintenanceEntity::getLocationId)
                .filter(Objects::nonNull)
                .distinct()
                .toList();

        Map<String, Object> lookupMap = basementService.getBatchBuFullNames(locationIds);

        List<MaintenanceSummaryDTO> content = maintenanceMapper.toSummaryList(maintenanceEntities, lookupMap);

        BasePageResponse<MaintenanceSummaryDTO> response = new BasePageResponse<>();
        response.setContent(content);
        response.setPageNumber(page.getNumber());
        response.setPageSize(page.getSize());
        response.setTotalPages(page.getTotalPages());
        response.setTotalElements(page.getTotalElements());
        response.setLastPage(page.isLast());

        return response;
    }

    @Override
//    @JaversAuditable
    public MaintenanceSummaryDTO updateMaintenance(Long id, @UnknownNullability MaintenanceUpdateRequest requestDto, List<MultipartFile> files) {
        MaintenanceRequestDto maintenanceRequestDto = requestDto.getRequestDto();
        AuditUpdateRequest auditUpdateRequest = requestDto.getAuditUpdateRequest();
        MaintenanceEntity maintenanceEntity = getMaintenanceById(id);
        assert maintenanceRequestDto.getMaintenancesStatus() != null;
        String status = maintenanceRequestDto.getMaintenancesStatus();
        String currentStatus = maintenanceEntity.getMaintenancesStatus();

        System.out.println("Request dto: "+ requestDto);
        if (!Objects.equals(currentStatus, status)) {
            if (!approvalEngineUtil.canTransition(status,currentStatus, ModuleEnum.MAINTENANCE )) {
                throw new IllegalStateException("Invalid status transition from " + maintenanceEntity.getMaintenancesStatus() + " to " + status);
            }
        }

        if (files != null && !files.isEmpty()) {
            String identify = ModuleEnum.MAINTENANCE +"-"+ id;
            attachmentService.addAttachment(files, identify, "maintenance", ModuleEnum.MAINTENANCE, false);
        }

        auditUpdateRequest.setModule(ModuleEnum.MAINTENANCE);
        auditUpdateRequest.setItemId(id.toString());

        auditUpdateService.createAuditUpdate(auditUpdateRequest);
        maintenanceMapper.updateEntityFromRequest(maintenanceRequestDto, maintenanceEntity);
        MaintenanceEntity saved = maintenanceRepository.save(maintenanceEntity);

        Map<String, Object > buFullNames = basementService.getBatchBuFullNames(List.of(maintenanceEntity.getLocationId()));
        return maintenanceMapper.fromEntityToMaintenanceInfoDto(saved,buFullNames);
    }

    @Override
    public String softDeleteMaintenance(Long maintenanceId) {
        MaintenanceEntity maintenanceEntity = getMaintenanceById(maintenanceId);
        maintenanceEntity.setIsDeleted(true);
        maintenanceRepository.save(maintenanceEntity);
        return "Soft deleted successfully";
    }

    @Override
    public String deleteMaintenanceById(Long id) {
        MaintenanceEntity maintenanceEntity = getMaintenanceById(id);
        maintenanceRepository.delete(maintenanceEntity);
        return "Deleted successfully";
    }

}
