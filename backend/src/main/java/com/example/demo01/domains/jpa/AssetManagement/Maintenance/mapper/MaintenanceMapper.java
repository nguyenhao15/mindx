package com.example.demo01.domains.jpa.AssetManagement.Maintenance.mapper;

import com.example.demo01.domains.jpa.AssetManagement.Dimmensions.mappers.MaintenanceCategoryMapper;
import com.example.demo01.domains.jpa.AssetManagement.Dimmensions.mappers.MaintenanceItemMapper;
import com.example.demo01.domains.jpa.AssetManagement.Maintenance.dtos.Maintenance.MaintenanceInfoDto;
import com.example.demo01.domains.jpa.AssetManagement.Maintenance.dtos.Maintenance.MaintenanceRequestDto;
import com.example.demo01.domains.jpa.AssetManagement.Maintenance.dtos.Maintenance.MaintenanceSummaryDTO;
import com.example.demo01.domains.jpa.AssetManagement.Maintenance.entities.MaintenanceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",
uses = {
        MaintenanceCategoryMapper .class,
        MaintenanceItemMapper.class
})
public interface MaintenanceMapper {

    MaintenanceSummaryDTO fromEntityToMaintenanceSummaryDTO(MaintenanceEntity entity);

    MaintenanceEntity fromRequestToEntityMaintenance(MaintenanceRequestDto requestDto);

    MaintenanceSummaryDTO fromEntityToMaintenanceInfoDto(MaintenanceEntity entity);

}
