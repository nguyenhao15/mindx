package com.example.demo01.domains.jpa.AssetManagement.Dimmensions.mappers;

import com.example.demo01.domains.jpa.AssetManagement.Dimmensions.dtos.MaintenanceItem.MaintenanceItemInfo;
import com.example.demo01.domains.jpa.AssetManagement.Dimmensions.dtos.MaintenanceItem.MaintenanceItemRequest;
import com.example.demo01.domains.jpa.AssetManagement.Dimmensions.entities.MaintenanceItemEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        uses = {MaintenanceCategoryMapper.class}
)
public interface MaintenanceItemMapper {

    MaintenanceItemEntity toEntity(MaintenanceItemRequest maintenanceItemRequest);

    MaintenanceItemInfo toInfo(MaintenanceItemEntity entity);

    MaintenanceItemInfo toInfoDto(MaintenanceItemEntity entity);
}
