package com.example.demo01.domains.jpa.AssetManagement.Maintenance.services.Impl;

import com.example.demo01.core.Basement.dto.basement.BUInfoDto;
import com.example.demo01.core.Basement.service.BasementService;
import com.example.demo01.domains.jpa.AssetManagement.Dimmensions.entities.MaintenanceCategoryEntity;
import com.example.demo01.domains.jpa.AssetManagement.Dimmensions.entities.MaintenanceItemEntity;
import com.example.demo01.domains.jpa.AssetManagement.Dimmensions.services.MaintenanceCategoryService;
import com.example.demo01.domains.jpa.AssetManagement.Dimmensions.services.MaintenanceItemService;
import com.example.demo01.domains.jpa.AssetManagement.Maintenance.dtos.Maintenance.MaintenanceRequestDto;
import com.example.demo01.domains.jpa.AssetManagement.Maintenance.dtos.Maintenance.MaintenanceSummaryDTO;
import com.example.demo01.domains.jpa.AssetManagement.Maintenance.entities.MaintenanceEntity;
import com.example.demo01.domains.jpa.AssetManagement.Maintenance.mapper.MaintenanceMapper;
import com.example.demo01.domains.jpa.AssetManagement.Maintenance.services.MaintenanceService;
import com.example.demo01.repository.postgreSQL.AssetManagement.MaintenanceRepository.MaintenanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MaintenanceServiceImpl implements MaintenanceService {

    @Autowired
    private MaintenanceRepository maintenanceRepository;

    @Autowired
    private MaintenanceItemService maintenanceItemService;

    @Autowired
    private MaintenanceCategoryService maintenanceCategoryService;

    @Autowired
    private MaintenanceMapper maintenanceMapper;

    @Autowired
    private BasementService  basementService;

    @Override
    public MaintenanceSummaryDTO createMaintenance(MaintenanceRequestDto requestDto) {
        Long categoryId = requestDto.getMaintenanceCategoryId();
        Long maintenanceItemId = requestDto.getMaintenanceItemId();
        String locationId = requestDto.getLocationId();

        MaintenanceCategoryEntity  categoryEntity = maintenanceCategoryService.getMaintenanceCategory(categoryId);
        MaintenanceItemEntity  itemEntity = maintenanceItemService.getMaintenanceItem(maintenanceItemId);
        BUInfoDto buInfoById = basementService.getBuInfoByShortName(locationId);

        MaintenanceEntity maintenanceEntity = maintenanceMapper.fromRequestToEntityMaintenance(requestDto);
        maintenanceEntity.setLocationId(buInfoById.getBuId());
        maintenanceEntity.setFixCategory(categoryEntity);
        maintenanceEntity.setFixItem(itemEntity);

        MaintenanceEntity maintenance = maintenanceRepository.save(maintenanceEntity);
        return maintenanceMapper.fromEntityToMaintenanceInfoDto(maintenance);
    }
}
