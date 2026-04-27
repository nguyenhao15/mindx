package com.example.demo01.domains.jpa.AssetManagement.Maintenance.services.Impl;

import com.example.demo01.core.AuditUpdate.model.ChangeType;
import com.example.demo01.domains.jpa.AssetManagement.Maintenance.dtos.Maintenance.MaintenanceDetailResponse;
import com.example.demo01.domains.jpa.AssetManagement.Maintenance.dtos.Maintenance.MaintenanceRequestDto;
import com.example.demo01.domains.jpa.AssetManagement.Maintenance.dtos.Maintenance.MaintenanceUpdateRequest;
import com.example.demo01.domains.jpa.AssetManagement.Maintenance.dtos.MaintenancesProposals.MaintenancesProposalRequest;
import com.example.demo01.domains.jpa.AssetManagement.Maintenance.dtos.MaintenancesProposals.MaintenancesProposalsDto;
import com.example.demo01.domains.jpa.AssetManagement.Maintenance.entities.MaintenanceEntity;
import com.example.demo01.domains.jpa.AssetManagement.Maintenance.services.MaintenanceService;
import com.example.demo01.domains.jpa.AssetManagement.Maintenance.services.MaintenanceWorkflow;
import com.example.demo01.domains.jpa.AssetManagement.Maintenance.services.MaintenancesProposalService;
import com.example.demo01.domains.jpa.AssetManagement.Utils.MaintenancesStatus;
import com.example.demo01.domains.jpa.Core.Audit.dto.AuditUpdateRequest;
import com.example.demo01.domains.jpa.Core.Audit.service.AuditUpdateService;
import com.example.demo01.utils.ModuleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class MaintenanceWorkflowServiceImpl implements MaintenanceWorkflow {

    @Autowired
    private MaintenanceService maintenanceService;

    @Autowired
    private MaintenancesProposalService maintenancesProposalService;

    @Autowired
    private AuditUpdateService auditUpdateService;

    @Override
    @Transactional(transactionManager = "postgreSQLTransactionManager")
    public MaintenanceDetailResponse createProposals(Long id, List<MaintenancesProposalRequest> requests) {
        List<MaintenancesProposalsDto> results = new ArrayList<>();
        MaintenanceEntity maintenance = maintenanceService.getReference(id);
        for (MaintenancesProposalRequest request : requests) {
            MaintenancesProposalsDto maintenancesProposalsDto = createProposal(request);
            results.add(maintenancesProposalsDto);
        }
        MaintenanceRequestDto maintenanceRequestDto = new MaintenanceRequestDto();
        AuditUpdateRequest auditUpdateRequest = new AuditUpdateRequest();
        if (Objects.equals(maintenance.getMaintenancesStatus(), MaintenancesStatus.APPROVED.toString())) {
            maintenanceRequestDto.setMaintenancesStatus(MaintenancesStatus.CHECKED.toString());
        } else {
            maintenanceRequestDto.setMaintenancesStatus(maintenance.getMaintenancesStatus());
        }

        auditUpdateRequest.setItemId(id.toString());
        auditUpdateRequest.setUpdateValue(maintenanceRequestDto.getMaintenancesStatus());
        auditUpdateRequest.setDescription("Added "+ requests.size() + " proposals to maintenance with id: " + id);
        auditUpdateRequest.setChangeType(ChangeType.UPDATE);

        MaintenanceUpdateRequest maintenanceUpdateRequest = new MaintenanceUpdateRequest(
                maintenanceRequestDto,
                auditUpdateRequest
        );
        maintenanceService.updateMaintenance(id, maintenanceUpdateRequest, List.of());
        return maintenanceService.getMaintenanceDetailsInfo(id);
    }

    @Override
    @Transactional(transactionManager = "postgreSQLTransactionManager")
    public MaintenancesProposalsDto createProposal(MaintenancesProposalRequest maintenancesProposalRequest) {
        MaintenanceEntity maintenance = maintenanceService.getReference(maintenancesProposalRequest.getMaintenanceId());
        return maintenancesProposalService.createProposal(maintenancesProposalRequest, maintenance);
    }

    @Override
    @Transactional(transactionManager = "postgreSQLTransactionManager")
    public MaintenanceDetailResponse updateProposal(Long id, MaintenancesProposalRequest maintenancesProposalRequest) {
        Long maintenanceId = maintenancesProposalRequest.getMaintenanceId();
        AuditUpdateRequest auditUpdateRequest = new AuditUpdateRequest();
        auditUpdateRequest.setItemId(maintenancesProposalRequest.getMaintenanceId().toString());
        auditUpdateRequest.setChangeType(ChangeType.UPDATE);
        auditUpdateRequest.setUpdateValue("UPDATED");
        auditUpdateRequest.setDescription("Updated proposal with id: " + id + " for maintenance with id: " + maintenancesProposalRequest.getMaintenanceId());
        auditUpdateRequest.setModule(ModuleEnum.MAINTENANCE);
        maintenancesProposalService.updateProposal(id, maintenancesProposalRequest);
        auditUpdateService.createAuditUpdate(auditUpdateRequest);
        return maintenanceService.getMaintenanceDetailsInfo(maintenanceId);
    }

    @Override
    public void deleteProposal(Long id) {
        maintenancesProposalService.deleteProposal(id);
    }
}
