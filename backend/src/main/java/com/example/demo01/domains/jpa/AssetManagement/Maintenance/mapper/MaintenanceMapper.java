package com.example.demo01.domains.jpa.AssetManagement.Maintenance.mapper;

import com.example.demo01.domains.jpa.AssetManagement.Dimmensions.mappers.MaintenanceCategoryMapper;
import com.example.demo01.domains.jpa.AssetManagement.Dimmensions.mappers.MaintenanceItemMapper;
import com.example.demo01.domains.jpa.AssetManagement.Maintenance.dtos.Maintenance.MaintenanceDetailsInfoDto;
import com.example.demo01.domains.jpa.AssetManagement.Maintenance.dtos.Maintenance.MaintenanceRequestDto;
import com.example.demo01.domains.jpa.AssetManagement.Maintenance.dtos.Maintenance.MaintenanceSummaryDTO;
import com.example.demo01.domains.jpa.AssetManagement.Maintenance.entities.MaintenanceEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
uses = {
        MaintenanceCategoryMapper .class,
        MaintenanceItemMapper.class,
        MaintenancesProposalMapper.class,
})
public interface MaintenanceMapper {

    MaintenanceEntity fromRequestToEntityMaintenance(MaintenanceRequestDto requestDto);

    @Mapping(target = "totalProposals", expression = "java(entity.getMaintenancesProposals().size())")
    MaintenanceSummaryDTO fromEntityToMaintenanceInfoDto(MaintenanceEntity entity);

    @Mapping(target = "totalProposals", expression = "java(entity.getMaintenancesProposals().size())")
    MaintenanceDetailsInfoDto fromEntityToMaintenanceDetailsInfoDto(MaintenanceEntity entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromRequest(MaintenanceRequestDto requestDto, @MappingTarget MaintenanceEntity entity);

}
