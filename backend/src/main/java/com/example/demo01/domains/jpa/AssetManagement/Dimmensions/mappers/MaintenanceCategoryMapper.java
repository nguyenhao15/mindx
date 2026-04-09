package com.example.demo01.domains.jpa.AssetManagement.Dimmensions.mappers;

import com.example.demo01.domains.jpa.AssetManagement.Dimmensions.dtos.MaintenanceCategory.MaintenanceCategoryInfo;
import com.example.demo01.domains.jpa.AssetManagement.Dimmensions.dtos.MaintenanceCategory.MaintenanceCategoryRequest;
import com.example.demo01.domains.jpa.AssetManagement.Dimmensions.entities.MaintenanceCategoryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MaintenanceCategoryMapper {

    MaintenanceCategoryEntity toEntity(MaintenanceCategoryRequest maintenanceCategoryRequest);

    MaintenanceCategoryInfo toInfo(MaintenanceCategoryEntity maintenanceCategoryEntity);

}
