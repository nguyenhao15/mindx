package com.example.demo01.domains.jpa.AssetManagement.Maintenance.services.Impl;

import com.example.demo01.domains.jpa.AssetManagement.Maintenance.dtos.MaintenancesProposals.MaintenancesProposalRequest;
import com.example.demo01.domains.jpa.AssetManagement.Maintenance.dtos.MaintenancesProposals.MaintenancesProposalsDto;
import com.example.demo01.domains.jpa.AssetManagement.Maintenance.entities.MaintenanceEntity;
import com.example.demo01.domains.jpa.AssetManagement.Maintenance.services.MaintenanceService;
import com.example.demo01.domains.jpa.AssetManagement.Maintenance.services.MaintenanceWorkflow;
import com.example.demo01.domains.jpa.AssetManagement.Maintenance.services.MaintenancesProposalService;
import com.example.demo01.domains.jpa.AssetManagement.Utils.MaintenancesStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class MaintenanceWorkflowServiceImpl implements MaintenanceWorkflow {

    @Autowired
    private MaintenanceService maintenanceService;

    @Autowired
    private MaintenancesProposalService maintenancesProposalService;

    @Override
    @Transactional
    public List<MaintenancesProposalsDto> createProposal(List<MaintenancesProposalRequest> requests) {
        List<MaintenancesProposalsDto> results = new ArrayList<>();
        for (MaintenancesProposalRequest request : requests) {
            MaintenanceEntity maintenance = maintenanceService.getReference(request.getMaintenanceId());
            if (maintenance.getMaintenancesStatus().toString().equals("APPROVED")) {
                maintenanceService.upadteMaintenanceStatus(request.getMaintenanceId(), MaintenancesStatus.CHECKED);
            }
            MaintenancesProposalsDto maintenancesProposalsDto = maintenancesProposalService.createProposal(request, maintenance);
            results.add(maintenancesProposalsDto);
        }

        return results;
    }
}
