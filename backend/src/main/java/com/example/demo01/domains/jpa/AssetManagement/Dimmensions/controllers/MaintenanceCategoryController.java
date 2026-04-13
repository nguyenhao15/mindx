package com.example.demo01.domains.jpa.AssetManagement.Dimmensions.controllers;

import com.example.demo01.domains.jpa.AssetManagement.Dimmensions.dtos.MaintenanceCategory.MaintenanceCategoryInfo;
import com.example.demo01.domains.jpa.AssetManagement.Dimmensions.dtos.MaintenanceCategory.MaintenanceCategoryInfoWithItems;
import com.example.demo01.domains.jpa.AssetManagement.Dimmensions.dtos.MaintenanceCategory.MaintenanceCategoryRequest;
import com.example.demo01.domains.jpa.AssetManagement.Dimmensions.services.MaintenanceCategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/asset/maintenance/dim/category")
public class MaintenanceCategoryController {

    @Autowired
    private MaintenanceCategoryService  maintenanceCategoryService;

    @PostMapping("/create")
    public ResponseEntity<?>  createMaintenanceCategory(@Valid @RequestBody  MaintenanceCategoryRequest request)
    {
        MaintenanceCategoryInfo categoryInfo = maintenanceCategoryService.createMaintenanceCategory(request);
        return ResponseEntity.ok(categoryInfo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMaintenanceCategory(@PathVariable Long id) {
        return ResponseEntity.ok(maintenanceCategoryService.getMaintenanceCategoryInfo(id));
    }

    @GetMapping("/provider/form")
    public ResponseEntity<?> getMaintenanceCategoryForValue() {
        List<MaintenanceCategoryInfoWithItems> categoryInfos = maintenanceCategoryService.getMaintenanceProvider();
        return ResponseEntity.ok(categoryInfos);
    }

}
