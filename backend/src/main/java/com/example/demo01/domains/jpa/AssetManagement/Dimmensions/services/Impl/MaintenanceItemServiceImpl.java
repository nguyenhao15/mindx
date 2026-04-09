package com.example.demo01.domains.jpa.AssetManagement.Dimmensions.services.Impl;


import com.example.demo01.domains.jpa.AssetManagement.Dimmensions.dtos.MaintenanceItem.MaintenanceItemInfo;
import com.example.demo01.domains.jpa.AssetManagement.Dimmensions.dtos.MaintenanceItem.MaintenanceItemRequest;
import com.example.demo01.domains.jpa.AssetManagement.Dimmensions.entities.MaintenanceCategoryEntity;
import com.example.demo01.domains.jpa.AssetManagement.Dimmensions.entities.MaintenanceItemEntity;
import com.example.demo01.domains.jpa.AssetManagement.Dimmensions.mappers.MaintenanceItemMapper;
import com.example.demo01.domains.jpa.AssetManagement.Dimmensions.services.MaintenanceCategoryService;
import com.example.demo01.domains.jpa.AssetManagement.Dimmensions.services.MaintenanceItemService;
import com.example.demo01.repository.postgreSQL.AssetManagement.Dimmensions.MaintenanceItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MaintenanceItemServiceImpl  implements MaintenanceItemService {

    @Autowired
    private MaintenanceItemRepository maintenanceItemRepository;

    @Autowired
    private MaintenanceItemMapper maintenanceItemMapper;

    @Autowired
    private MaintenanceCategoryService  maintenanceCategoryService;

    @Override
    public MaintenanceItemEntity getMaintenanceItem(Long maintenanceItemId) {
        return maintenanceItemRepository.findById(maintenanceItemId)
                .orElseThrow(() -> new RuntimeException("Maintenance Item not found with id: " + maintenanceItemId));
    }

    @Override
    public MaintenanceItemInfo createMaintenanceItem(MaintenanceItemRequest maintenanceItemRequest) {
        MaintenanceCategoryEntity category = maintenanceCategoryService.getMaintenanceCategory(maintenanceItemRequest.getCategoryId());

        MaintenanceItemEntity maintenanceItemEntity = maintenanceItemMapper.toEntity(maintenanceItemRequest);
        maintenanceItemEntity.setMaintenanceCategory(category);
        MaintenanceItemEntity savedEntity = maintenanceItemRepository.save(maintenanceItemEntity);
        return maintenanceItemMapper.toInfo(savedEntity);
    }
}
