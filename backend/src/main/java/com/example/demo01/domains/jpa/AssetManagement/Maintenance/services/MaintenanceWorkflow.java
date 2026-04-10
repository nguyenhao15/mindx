package com.example.demo01.domains.jpa.AssetManagement.Maintenance.services;

import com.example.demo01.domains.jpa.AssetManagement.Maintenance.dtos.MaintenancesProposals.MaintenancesProposalRequest;
import com.example.demo01.domains.jpa.AssetManagement.Maintenance.dtos.MaintenancesProposals.MaintenancesProposalsDto;

import java.util.List;

public interface MaintenanceWorkflow {

    List<MaintenancesProposalsDto> createProposal(List<MaintenancesProposalRequest> requests);

}
