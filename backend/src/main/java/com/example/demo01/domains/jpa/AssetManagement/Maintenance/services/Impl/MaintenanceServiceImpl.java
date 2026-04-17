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
import com.example.demo01.domains.jpa.AssetManagement.Utils.MaintenancesStatus;
import com.example.demo01.domains.jpa.Core.Audit.dto.AuditUpdateDto;
import com.example.demo01.domains.jpa.Core.Audit.dto.AuditUpdateRequest;
import com.example.demo01.domains.jpa.Core.Audit.service.AuditUpdateService;
import com.example.demo01.repository.postgreSQL.AssetManagement.MaintenanceRepository.MaintenanceRepository;
import com.example.demo01.utils.*;
import com.example.demo01.utils.Query.PostgreSQL.DynamicSpecificationBuilder;
import com.example.demo01.utils.Query.PostgreSQL.PostgreSQLPageUtil;
import org.javers.spring.annotation.JaversAuditable;
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
    private PostgreSQLPageUtil postgreSQLPageUtil;

    @Autowired
    private MaintenanceMapper maintenanceMapper;

    @Autowired
    private BasementService  basementService;

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
    public MaintenanceDetailResponse getMaintenanceDetailsInfo(Long maintenanceId) {
        MaintenanceDetailResponse maintenanceDetailResponse = new MaintenanceDetailResponse();
        MaintenanceEntity maintenanceEntity = getMaintenanceById(maintenanceId);
        MaintenanceDetailsInfoDto detailsInfoDto = maintenanceMapper.fromEntityToMaintenanceDetailsInfoDto(maintenanceEntity);
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
    public BasePageResponse<MaintenanceSummaryDTO> getBasePageResponseWithFilter(FilterWithPagination filterWithPagination) {
        PageInput pageInput = filterWithPagination.getPagination();
        List<FilterRequest> filters = filterWithPagination.getFilters();
        Map<String, Specification<MaintenanceEntity>> specification = Map.of(
                "isDeleted", (root, query, cb) -> cb.isFalse(root.get("isDeleted"))
        );
        Specification<MaintenanceEntity> finalSpecification = dynamicSpecificationBuilder.build(filters, specification);

        Page<MaintenanceEntity> page = postgreSQLPageUtil.buildPageResponse(
                finalSpecification,
                pageInput,
                maintenanceRepository
        );

        return buildPageResponse(page) ;
    }

    @Override
    public BasePageResponse<MaintenanceSummaryDTO> buildPageResponse(Page<MaintenanceEntity> page) {
         List<MaintenanceSummaryDTO> content = page.getContent().stream()
                .map(maintenanceMapper::fromEntityToMaintenanceInfoDto)
                .toList();
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
    @JaversAuditable
    public MaintenanceSummaryDTO updateMaintenance(Long id, @UnknownNullability MaintenanceUpdateRequest requestDto) {
        MaintenanceRequestDto maintenanceRequestDto = requestDto.getRequestDto();
        AuditUpdateRequest auditUpdateRequest = requestDto.getAuditUpdateRequest();

        MaintenanceEntity maintenanceEntity = getMaintenanceById(id);
        if (canTransition(maintenanceEntity.getMaintenancesStatus(), maintenanceRequestDto.getMaintenancesStatus())) {
            maintenanceEntity.setMaintenancesStatus(maintenanceRequestDto.getMaintenancesStatus());
        } else if (maintenanceRequestDto.getMaintenancesStatus() != null && !maintenanceEntity.getMaintenancesStatus().equals(maintenanceRequestDto.getMaintenancesStatus())) {
            throw new IllegalStateException("Invalid status transition from " + maintenanceEntity.getMaintenancesStatus() + " to " + maintenanceRequestDto.getMaintenancesStatus());
        }

        assert maintenanceRequestDto.getMaintenancesStatus() != null;

        auditUpdateRequest.setModule(ModuleEnum.MAINTENANCE);
        auditUpdateRequest.setIdentifier(ModuleEnum.MAINTENANCE + "-" + id);

        auditUpdateService.createAuditUpdate(auditUpdateRequest);

        if (maintenanceEntity.getMaintenancesStatus() !=maintenanceRequestDto.getMaintenancesStatus()) {
            return updateMaintenanceStatus(id, maintenanceRequestDto.getMaintenancesStatus());
        }

        maintenanceMapper.updateEntityFromRequest(maintenanceRequestDto, maintenanceEntity);
        maintenanceRepository.save(maintenanceEntity);
        return maintenanceMapper.fromEntityToMaintenanceInfoDto(maintenanceEntity);
    }

    @Override
    public String softDeleteMaintenance(Long maintenanceId) {
        MaintenanceEntity maintenanceEntity = getMaintenanceById(maintenanceId);
        maintenanceEntity.setIsDeleted(true);
        maintenanceRepository.save(maintenanceEntity);
        return "Soft deleted successfully";
    }

    @Override
    public MaintenanceSummaryDTO updateMaintenanceStatus(Long id, MaintenancesStatus status) {
        MaintenanceEntity maintenanceEntity = getMaintenanceById(id);
        if (status == null || maintenanceEntity.getMaintenancesStatus() == null ) return null;
        if (!canTransition(maintenanceEntity.getMaintenancesStatus(), status)) {
            throw new IllegalStateException("Invalid status transition from " + maintenanceEntity.getMaintenancesStatus() + " to " + status);
        }
        if (maintenanceEntity.getMaintenancesStatus().equals(MaintenancesStatus.FINISHED) && status.equals(MaintenancesStatus.WAITING)) {
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
