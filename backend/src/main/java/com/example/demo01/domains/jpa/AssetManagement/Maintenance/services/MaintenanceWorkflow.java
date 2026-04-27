package com.example.demo01.domains.jpa.AssetManagement.Maintenance.services;

import com.example.demo01.domains.jpa.AssetManagement.Maintenance.dtos.Maintenance.MaintenanceDetailResponse;
import com.example.demo01.domains.jpa.AssetManagement.Maintenance.dtos.MaintenancesProposals.MaintenancesProposalRequest;
import com.example.demo01.domains.jpa.AssetManagement.Maintenance.dtos.MaintenancesProposals.MaintenancesProposalsDto;

import java.util.List;

public interface MaintenanceWorkflow {

    MaintenanceDetailResponse createProposals(Long id , List<MaintenancesProposalRequest> requests);

    MaintenancesProposalsDto createProposal(MaintenancesProposalRequest maintenancesProposalRequest);

    MaintenanceDetailResponse updateProposal(Long id, MaintenancesProposalRequest maintenancesProposalRequest);

    void deleteProposal(Long id);
}
