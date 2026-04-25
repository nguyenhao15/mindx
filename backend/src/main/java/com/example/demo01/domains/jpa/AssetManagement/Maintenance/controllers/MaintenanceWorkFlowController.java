package com.example.demo01.domains.jpa.AssetManagement.Maintenance.controllers;

import com.example.demo01.domains.jpa.AssetManagement.Maintenance.dtos.Maintenance.MaintenanceDetailResponse;
import com.example.demo01.domains.jpa.AssetManagement.Maintenance.dtos.MaintenancesProposals.MaintenancesProposalRequest;
import com.example.demo01.domains.jpa.AssetManagement.Maintenance.dtos.MaintenancesProposals.MaintenancesProposalsDto;
import com.example.demo01.domains.jpa.AssetManagement.Maintenance.services.MaintenanceWorkflow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/asset/maintenance/workflow")
public class MaintenanceWorkFlowController {

    @Autowired
    MaintenanceWorkflow maintenanceWorkflow;

    @PostMapping("/proposal/create/{id}")
    public ResponseEntity<?> createProposal(@PathVariable Long id, @RequestBody List<MaintenancesProposalRequest> requests){
        MaintenanceDetailResponse results = maintenanceWorkflow.createProposals(id, requests);
        return ResponseEntity.ok().body(results);
    }

}
