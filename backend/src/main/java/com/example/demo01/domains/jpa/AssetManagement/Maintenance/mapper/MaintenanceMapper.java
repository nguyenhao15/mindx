package com.example.demo01.domains.jpa.AssetManagement.Maintenance.mapper;

import com.example.demo01.domains.jpa.AssetManagement.Maintenance.dtos.Maintenance.MaintenanceInfoDto;
import com.example.demo01.domains.jpa.AssetManagement.Maintenance.dtos.Maintenance.MaintenanceRequestDto;
import com.example.demo01.domains.jpa.AssetManagement.Maintenance.entities.MaintenanceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MaintenanceMapper {


    MaintenanceEntity fromRequestToEntityMaintenance(MaintenanceRequestDto requestDto);

    MaintenanceInfoDto  fromEntityToMaintenanceInfoDto(MaintenanceEntity entity);

}
