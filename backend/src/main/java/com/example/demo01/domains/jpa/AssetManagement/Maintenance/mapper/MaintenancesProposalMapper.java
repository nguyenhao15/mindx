package com.example.demo01.domains.jpa.AssetManagement.Maintenance.mapper;

import com.example.demo01.domains.jpa.AssetManagement.Maintenance.dtos.MaintenancesProposals.MaintenancesProposalRequest;
import com.example.demo01.domains.jpa.AssetManagement.Maintenance.dtos.MaintenancesProposals.MaintenancesProposalsDto;
import com.example.demo01.domains.jpa.AssetManagement.Maintenance.entities.MaintenancesProposals;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface MaintenancesProposalMapper {

    MaintenancesProposalsDto toProposalInfoDto(MaintenancesProposals proposal);
    MaintenancesProposals toEntity(MaintenancesProposalRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(MaintenancesProposalRequest request, @MappingTarget MaintenancesProposals proposal);
}
