package com.example.demo01.domains.jpa.AssetManagement.Maintenance.mapper;

import com.example.demo01.domains.jpa.AssetManagement.Maintenance.dtos.MaintenancesProposals.MaintenancesProposalRequest;
import com.example.demo01.domains.jpa.AssetManagement.Maintenance.dtos.MaintenancesProposals.MaintenancesProposalsDto;
import com.example.demo01.domains.jpa.AssetManagement.Maintenance.entities.MaintenancesProposals;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MaintenancesProposalMapper {
    MaintenancesProposalsDto toProposalInfoDto(MaintenancesProposals proposal);

    MaintenancesProposals toEntity(MaintenancesProposalRequest request);
}
