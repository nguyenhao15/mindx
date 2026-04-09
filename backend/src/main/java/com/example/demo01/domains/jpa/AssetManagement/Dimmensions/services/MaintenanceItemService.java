package com.example.demo01.domains.jpa.AssetManagement.Dimmensions.services;

import com.example.demo01.domains.jpa.AssetManagement.Dimmensions.dtos.MaintenanceItem.MaintenanceItemInfo;
import com.example.demo01.domains.jpa.AssetManagement.Dimmensions.dtos.MaintenanceItem.MaintenanceItemRequest;

public interface MaintenanceItemService {

    MaintenanceItemInfo createMaintenanceItem(MaintenanceItemRequest maintenanceItemRequest);

}
