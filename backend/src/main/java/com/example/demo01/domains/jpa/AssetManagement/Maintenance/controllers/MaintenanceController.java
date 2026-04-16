package com.example.demo01.domains.jpa.AssetManagement.Maintenance.controllers;

import com.example.demo01.domains.jpa.AssetManagement.Maintenance.dtos.Maintenance.MaintenanceRequestDto;
import com.example.demo01.domains.jpa.AssetManagement.Maintenance.dtos.Maintenance.MaintenanceSummaryDTO;
import com.example.demo01.domains.jpa.AssetManagement.Maintenance.dtos.Maintenance.MaintenanceUpdateRequest;
import com.example.demo01.domains.jpa.AssetManagement.Maintenance.services.MaintenanceService;
import com.example.demo01.domains.jpa.AssetManagement.Maintenance.services.MaintenanceWorkflow;
import com.example.demo01.domains.jpa.AssetManagement.Utils.MaintenancesStatus;
import com.example.demo01.utils.BasePageResponse;
import com.example.demo01.utils.FilterWithPagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/asset/maintenance/request")
public class MaintenanceController {

    @Autowired
    MaintenanceService maintenanceService;

    @Autowired
    MaintenanceWorkflow maintenanceWorkflow;

    @PostMapping(value = "/create",consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> createMaintenance(@RequestPart(value = "data") MaintenanceRequestDto requestDto,
                                               @RequestPart(value = "files") List<MultipartFile> files) {
        MaintenanceSummaryDTO maintenanceInfo = maintenanceService.createMaintenance(requestDto, files);
        return ResponseEntity.ok(maintenanceInfo);
    }

    @PostMapping("/get/page")
    public ResponseEntity<?> getMaintenanceItemWithPage(@RequestBody FilterWithPagination filterWithPagination) {
        BasePageResponse<MaintenanceSummaryDTO> maintenancePage = maintenanceService.getBasePageResponseWithFilter(filterWithPagination);
        return ResponseEntity.ok(maintenancePage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMaintenanceById(@PathVariable Long id){
        return ResponseEntity.ok(maintenanceService.getMaintenanceDetailsInfo(id));
    }

    @PutMapping("/update-status/{id}")
    public ResponseEntity<?> updateMaintenanceStatus(@PathVariable Long id, @RequestParam("status") MaintenancesStatus MaintenanceStatus) {
        MaintenanceSummaryDTO updatedMaintenance = maintenanceService.updateMaintenanceStatus(id, MaintenanceStatus);
        return ResponseEntity.ok(updatedMaintenance);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateMaintenance(@PathVariable Long id, @RequestBody MaintenanceUpdateRequest requestDto) {
        MaintenanceSummaryDTO maintenanceSummaryDTO = maintenanceService.updateMaintenance(id, requestDto);
        return ResponseEntity.ok(maintenanceSummaryDTO);
    }

    @DeleteMapping("/sort-delete/{maintenanceId}")
    public ResponseEntity<?> sortDeleteMaintenance(@PathVariable Long maintenanceId) {
        String successMessage = maintenanceService.softDeleteMaintenance(maintenanceId);
        return ResponseEntity.ok(successMessage);
    }
}
