package com.example.demo01.domains.jpa.AssetManagement.Dimmensions.services;

import com.example.demo01.domains.jpa.AssetManagement.Dimmensions.dtos.MaintenanceItem.MaintenanceItemInfo;
import com.example.demo01.domains.jpa.AssetManagement.Dimmensions.dtos.MaintenanceItem.MaintenanceItemRequest;
import com.example.demo01.domains.jpa.AssetManagement.Dimmensions.entities.MaintenanceItemEntity;

public interface MaintenanceItemService {

    MaintenanceItemEntity getMaintenanceItem(Long maintenanceItemId);

    MaintenanceItemInfo createMaintenanceItem(MaintenanceItemRequest maintenanceItemRequest);

}
