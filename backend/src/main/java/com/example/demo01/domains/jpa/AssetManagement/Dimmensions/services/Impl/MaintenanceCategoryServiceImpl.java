package com.example.demo01.domains.jpa.AssetManagement.Dimmensions.services.Impl;

import com.example.demo01.core.Exceptions.ResourceNotFoundException;
import com.example.demo01.domains.jpa.AssetManagement.Dimmensions.dtos.MaintenanceCategory.MaintenanceCategoryInfo;
import com.example.demo01.domains.jpa.AssetManagement.Dimmensions.dtos.MaintenanceCategory.MaintenanceCategoryInfoWithItems;
import com.example.demo01.domains.jpa.AssetManagement.Dimmensions.dtos.MaintenanceCategory.MaintenanceCategoryRequest;
import com.example.demo01.domains.jpa.AssetManagement.Dimmensions.entities.MaintenanceCategoryEntity;
import com.example.demo01.domains.jpa.AssetManagement.Dimmensions.mappers.MaintenanceCategoryMapper;
import com.example.demo01.domains.jpa.AssetManagement.Dimmensions.services.MaintenanceCategoryService;
import com.example.demo01.repository.postgreSQL.AssetManagement.Dimmensions.MaintenanceCategoryRepository;
import com.example.demo01.utils.PageInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MaintenanceCategoryServiceImpl implements MaintenanceCategoryService {

    @Autowired
    private MaintenanceCategoryRepository maintenanceCategoryRepository;

    @Autowired
    private MaintenanceCategoryMapper maintenanceCategoryMapper;

    @Override
    public MaintenanceCategoryInfo createMaintenanceCategory(MaintenanceCategoryRequest request) {
        MaintenanceCategoryEntity maintenanceCategory = maintenanceCategoryMapper.toEntity(request);
        MaintenanceCategoryEntity savedEntity = maintenanceCategoryRepository.save(maintenanceCategory);
        return maintenanceCategoryMapper.toInfo(savedEntity);
    }

    @Override
    public MaintenanceCategoryEntity getMaintenanceCategory(Long id) {
        return maintenanceCategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MaintenanceCategory","id", id));
    }

    @Override
    public MaintenanceCategoryInfoWithItems getMaintenanceCategoryInfo(Long id) {
        MaintenanceCategoryEntity maintenanceCategory = getMaintenanceCategory(id);
        return maintenanceCategoryMapper.fromEntityToInfoWithItems(maintenanceCategory);
    }

}
