package com.example.demo01.domains.jpa.AssetManagement.Maintenance.services.Impl;

import com.example.demo01.core.Basement.dto.basement.BUInfoDto;
import com.example.demo01.core.Basement.service.BasementService;
import com.example.demo01.domains.jpa.AssetManagement.Maintenance.dtos.Maintenance.MaintenanceInfoDto;
import com.example.demo01.domains.jpa.AssetManagement.Maintenance.dtos.Maintenance.MaintenanceRequestDto;
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
    private MaintenanceMapper maintenanceMapper;

    @Autowired
    private BasementService  basementService;

    @Override
    public MaintenanceInfoDto createMaintenance(MaintenanceRequestDto requestDto) {
        String locationId = requestDto.getLocationId();
        BUInfoDto buInfoById = basementService.getBuInfoById(locationId);
        MaintenanceEntity maintenanceEntity = maintenanceMapper.fromRequestToEntityMaintenance(requestDto);
        maintenanceEntity.setLocationId(buInfoById.getBuId());

        MaintenanceEntity maintenance = maintenanceRepository.save(maintenanceEntity);
        return maintenanceMapper.fromEntityToMaintenanceInfoDto(maintenance);
    }
}
