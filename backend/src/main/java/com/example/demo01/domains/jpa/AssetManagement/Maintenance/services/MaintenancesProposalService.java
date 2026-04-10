package com.example.demo01.domains.jpa.AssetManagement.Maintenance.services;

import com.example.demo01.domains.jpa.AssetManagement.Maintenance.dtos.MaintenancesProposals.MaintenancesProposalRequest;
import com.example.demo01.domains.jpa.AssetManagement.Maintenance.dtos.MaintenancesProposals.MaintenancesProposalsDto;
import com.example.demo01.domains.jpa.AssetManagement.Maintenance.entities.MaintenanceEntity;
import com.example.demo01.domains.jpa.AssetManagement.Maintenance.entities.MaintenancesProposals;

public interface MaintenancesProposalService {

    MaintenancesProposalsDto createProposal(MaintenancesProposalRequest request, MaintenanceEntity maintenance);

    MaintenancesProposals getProposalById(Long id);

    MaintenancesProposalsDto getProposalDtoById(Long id);

    MaintenancesProposalsDto updateProposal(Long id, MaintenancesProposalRequest request);

    String deleteProposal(Long id);

}
