package com.example.demo01.domains.jpa.AssetManagement.Maintenance.mapper;

import com.example.demo01.domains.jpa.AssetManagement.Maintenance.dtos.MaintenancesProposals.ProposalInfoDto;
import com.example.demo01.domains.jpa.AssetManagement.Maintenance.entities.MaintenancesProposals;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MaintenancesProposalMapper {
    ProposalInfoDto toProposalInfoDto(MaintenancesProposals proposal);

}
