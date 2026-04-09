package com.example.demo01.domains.jpa.AssetManagement.Dimmensions.controllers;

import com.example.demo01.domains.jpa.AssetManagement.Dimmensions.dtos.MaintenanceCategory.MaintenanceCategoryInfo;
import com.example.demo01.domains.jpa.AssetManagement.Dimmensions.dtos.MaintenanceCategory.MaintenanceCategoryRequest;
import com.example.demo01.domains.jpa.AssetManagement.Dimmensions.services.MaintenanceCategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/maintenance/category")
public class MaintenanceCategoryController {

    @Autowired
    private MaintenanceCategoryService  maintenanceCategoryService;

    @PostMapping("/create")
    public ResponseEntity<?>  createMaintenanceCategory(@Valid @RequestBody  MaintenanceCategoryRequest request)
    {
        MaintenanceCategoryInfo categoryInfo = maintenanceCategoryService.createMaintenanceCategory(request);
        return ResponseEntity.ok(categoryInfo);
    }

}
