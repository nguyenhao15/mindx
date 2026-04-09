package com.example.demo01.domains.jpa.AssetManagement.Dimmensions.mappers;

import com.example.demo01.domains.jpa.AssetManagement.Dimmensions.dtos.MaintenanceCategory.MaintenanceCategoryInfo;
import com.example.demo01.domains.jpa.AssetManagement.Dimmensions.dtos.MaintenanceCategory.MaintenanceCategoryInfoWithItems;
import com.example.demo01.domains.jpa.AssetManagement.Dimmensions.dtos.MaintenanceCategory.MaintenanceCategoryNestInfo;
import com.example.demo01.domains.jpa.AssetManagement.Dimmensions.dtos.MaintenanceCategory.MaintenanceCategoryRequest;
import com.example.demo01.domains.jpa.AssetManagement.Dimmensions.entities.MaintenanceCategoryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
    uses = {MaintenanceItemMapper.class}
)
public interface MaintenanceCategoryMapper {

    MaintenanceCategoryEntity toEntity(MaintenanceCategoryRequest maintenanceCategoryRequest);

    MaintenanceCategoryInfoWithItems fromEntityToInfoWithItems(MaintenanceCategoryEntity maintenanceItemEntity);

    MaintenanceCategoryInfo toInfo(MaintenanceCategoryEntity maintenanceCategoryEntity);

    MaintenanceCategoryNestInfo fromInfoToNestInfo(MaintenanceCategoryEntity maintenanceCategoryEntity);

}
