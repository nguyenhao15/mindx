package com.example.demo01.domains.jpa.AssetManagement.Maintenance.services.Impl;

import com.example.demo01.core.Exceptions.ResourceNotFoundException;
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
    private MaintenancesProposalMapper maintenancesProposalMapper;

    @Override
    public MaintenancesProposalsDto createProposal(MaintenancesProposalRequest request, MaintenanceEntity maintenance) {
        MaintenancesProposals proposal = maintenancesProposalMapper.toEntity(request);
        proposal.setMaintenance(maintenance);
        maintenancesProposalRepository.save(proposal);
        return maintenancesProposalMapper.toProposalInfoDto(proposal);
    }

    @Override
    public MaintenancesProposals getProposalById(Long id) {
        return maintenancesProposalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MaintenancesProposal", "id", id));
    }

    @Override
    public MaintenancesProposalsDto getProposalDtoById(Long id) {
        MaintenancesProposals maintenancesProposals =  getProposalById(id);
        return maintenancesProposalMapper.toProposalInfoDto(maintenancesProposals);
    }

    @Override
    public MaintenancesProposalsDto updateProposal(Long id, MaintenancesProposalRequest request) {
        MaintenancesProposals maintenancesProposals =  getProposalById(id);
        maintenancesProposalMapper.updateEntityFromDto(request, maintenancesProposals);
        MaintenancesProposals savedItem = maintenancesProposalRepository.save(maintenancesProposals);
        return maintenancesProposalMapper.toProposalInfoDto(savedItem);
    }

    @Override
    public String softDeleteProposalById(Long id) {
        MaintenancesProposals maintenancesProposals =  getProposalById(id);
        maintenancesProposals.setIsDeleted(true);
        maintenancesProposalRepository.save(maintenancesProposals);
        return "Proposal with id " + id + " has been soft deleted.";
    }

    @Override
    public String deleteProposal(Long id) {
        MaintenancesProposals maintenancesProposals =  getProposalById(id);
        maintenancesProposalRepository.delete(maintenancesProposals);
        return "Proposal with id " + id + " has been permanently deleted.";
    }
}
