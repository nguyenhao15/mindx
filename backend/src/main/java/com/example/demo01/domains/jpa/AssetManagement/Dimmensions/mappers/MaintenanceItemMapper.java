package com.example.demo01.domains.jpa.AssetManagement.Dimmensions.mappers;

import com.example.demo01.domains.jpa.AssetManagement.Dimmensions.dtos.MaintenanceItem.MaintenanceItemInfo;
import com.example.demo01.domains.jpa.AssetManagement.Dimmensions.dtos.MaintenanceItem.MaintenanceItemInfoDto;
import com.example.demo01.domains.jpa.AssetManagement.Dimmensions.dtos.MaintenanceItem.MaintenanceItemRequest;
import com.example.demo01.domains.jpa.AssetManagement.Dimmensions.entities.MaintenanceItemEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MaintenanceItemMapper {

    MaintenanceItemEntity toEntity(MaintenanceItemRequest maintenanceItemRequest);

    MaintenanceItemInfo toInfo(MaintenanceItemEntity entity);

    MaintenanceItemInfoDto toInfoDto(MaintenanceItemEntity entity);
}
