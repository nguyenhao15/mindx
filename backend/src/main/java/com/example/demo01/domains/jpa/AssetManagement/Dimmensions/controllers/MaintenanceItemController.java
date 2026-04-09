package com.example.demo01.domains.jpa.AssetManagement.Dimmensions.controllers;

import com.example.demo01.domains.jpa.AssetManagement.Dimmensions.dtos.MaintenanceItem.MaintenanceItemInfo;
import com.example.demo01.domains.jpa.AssetManagement.Dimmensions.dtos.MaintenanceItem.MaintenanceItemRequest;
import com.example.demo01.domains.jpa.AssetManagement.Dimmensions.services.MaintenanceItemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/maintenance/dim/maitenance-item")
public class MaintenanceItemController {

    @Autowired
    private MaintenanceItemService maintenanceItemService;

    @PostMapping("/create")
    public ResponseEntity<?> createMaintenanceItem(@Valid @RequestBody MaintenanceItemRequest  maintenanceItemRequest) {
        MaintenanceItemInfo maintenanceItemInfo = maintenanceItemService.createMaintenanceItem(maintenanceItemRequest);
        return ResponseEntity.ok().body(maintenanceItemInfo);
    }

}
