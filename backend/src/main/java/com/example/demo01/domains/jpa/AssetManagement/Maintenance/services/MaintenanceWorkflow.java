package com.example.demo01.domains.jpa.AssetManagement.Maintenance.services;

import com.example.demo01.domains.jpa.AssetManagement.Maintenance.dtos.MaintenancesProposals.MaintenancesProposalRequest;
import com.example.demo01.domains.jpa.AssetManagement.Maintenance.dtos.MaintenancesProposals.MaintenancesProposalsDto;
import com.example.demo01.domains.jpa.AssetManagement.Maintenance.entities.MaintenancesProposals;
import com.example.demo01.utils.BasePageResponse;
import com.example.demo01.utils.FilterWithPagination;

import java.util.List;

public interface MaintenanceWorkflow {

    List<MaintenancesProposalsDto> createProposals(List<MaintenancesProposalRequest> requests);

    MaintenancesProposalsDto createProposal(MaintenancesProposalRequest maintenancesProposalRequest);


}
