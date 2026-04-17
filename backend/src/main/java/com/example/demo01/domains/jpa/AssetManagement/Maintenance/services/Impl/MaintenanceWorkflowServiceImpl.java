package com.example.demo01.domains.jpa.AssetManagement.Maintenance.services.Impl;

import com.example.demo01.domains.jpa.AssetManagement.Maintenance.dtos.Maintenance.MaintenanceRequestDto;
import com.example.demo01.domains.jpa.AssetManagement.Maintenance.dtos.MaintenancesProposals.MaintenancesProposalRequest;
import com.example.demo01.domains.jpa.AssetManagement.Maintenance.dtos.MaintenancesProposals.MaintenancesProposalsDto;
import com.example.demo01.domains.jpa.AssetManagement.Maintenance.entities.MaintenanceEntity;
import com.example.demo01.domains.jpa.AssetManagement.Maintenance.mapper.MaintenanceMapper;
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

    @Autowired
    private MaintenanceMapper maintenanceMapper;

    @Override
    @Transactional
    public List<MaintenancesProposalsDto> createProposals(List<MaintenancesProposalRequest> requests) {
        List<MaintenancesProposalsDto> results = new ArrayList<>();
        for (MaintenancesProposalRequest request : requests) {
            MaintenancesProposalsDto maintenancesProposalsDto = createProposal(request);
            results.add(maintenancesProposalsDto);
        }
        return results;
    }

    @Override
    @Transactional
    public MaintenancesProposalsDto createProposal(MaintenancesProposalRequest maintenancesProposalRequest) {
        MaintenanceEntity maintenance = maintenanceService.getReference(maintenancesProposalRequest.getMaintenanceId());
        return maintenancesProposalService.createProposal(maintenancesProposalRequest, maintenance);
    }
}
