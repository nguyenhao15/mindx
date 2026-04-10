package com.example.demo01.domains.jpa.AssetManagement.Maintenance.services.Impl;

import com.example.demo01.domains.jpa.AssetManagement.Maintenance.dtos.MaintenancesProposals.MaintenancesProposalRequest;
import com.example.demo01.domains.jpa.AssetManagement.Maintenance.dtos.MaintenancesProposals.MaintenancesProposalsDto;
import com.example.demo01.domains.jpa.AssetManagement.Maintenance.entities.MaintenanceEntity;
import com.example.demo01.domains.jpa.AssetManagement.Maintenance.entities.MaintenancesProposals;
import com.example.demo01.domains.jpa.AssetManagement.Maintenance.mapper.MaintenancesProposalMapper;
import com.example.demo01.domains.jpa.AssetManagement.Maintenance.services.MaintenancesProposalService;
import com.example.demo01.repository.postgreSQL.AssetManagement.MaintenanceRepository.MaintenancesProposalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MaintenancesProposalServiceIml implements MaintenancesProposalService {

    @Autowired
    private MaintenancesProposalRepository  maintenancesProposalRepository;

    @Autowired
    private MaintenancesProposalMapper  maintenancesProposalMapper;

    @Override
    public MaintenancesProposalsDto createProposal(MaintenancesProposalRequest request, MaintenanceEntity maintenance) {
        MaintenancesProposals proposal = maintenancesProposalMapper.toEntity(request);
        proposal.setMaintenance(maintenance);
        maintenancesProposalRepository.save(proposal);
        return maintenancesProposalMapper.toProposalInfoDto(proposal);
    }

    @Override
    public MaintenancesProposals getProposalById(Long id) {
        return null;
    }

    @Override
    public MaintenancesProposalsDto getProposalDtoById(Long id) {
        return null;
    }

    @Override
    public MaintenancesProposalsDto updateProposal(Long id, MaintenancesProposalRequest request) {
        return null;
    }

    @Override
    public String deleteProposal(Long id) {
        return "";
    }
}
