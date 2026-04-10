package com.example.demo01.domains.jpa.AssetManagement.Maintenance.controllers;

import com.example.demo01.domains.jpa.AssetManagement.Maintenance.dtos.Maintenance.MaintenanceRequestDto;
import com.example.demo01.domains.jpa.AssetManagement.Maintenance.dtos.Maintenance.MaintenanceSummaryDTO;
import com.example.demo01.domains.jpa.AssetManagement.Maintenance.services.MaintenanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/maintenance/request")
public class MaintenanceController {

    @Autowired
    MaintenanceService maintenanceService;

    @PostMapping("/create")
    public ResponseEntity<?> createMaintenance(@RequestBody MaintenanceRequestDto requestDto) {
        MaintenanceSummaryDTO maintenanceInfo = maintenanceService.createMaintenance(requestDto);
        return ResponseEntity.ok(maintenanceInfo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMaintenanceById(@PathVariable Long id){
        return ResponseEntity.ok(maintenanceService.getMaintenanceSummaryById(id));
    }

    @DeleteMapping("/sort-delete/{maintenanceId}")
    public ResponseEntity<?> sortDeleteMaintenance(@PathVariable Long maintenanceId) {
        String successMessage = maintenanceService.softDeleteMaintenance(maintenanceId);
        return ResponseEntity.ok(successMessage);
    }
}
