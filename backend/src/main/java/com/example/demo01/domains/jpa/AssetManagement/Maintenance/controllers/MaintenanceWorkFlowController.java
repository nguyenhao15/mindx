package com.example.demo01.domains.jpa.AssetManagement.Maintenance.controllers;

import com.example.demo01.domains.jpa.AssetManagement.Maintenance.dtos.MaintenancesProposals.MaintenancesProposalRequest;
import com.example.demo01.domains.jpa.AssetManagement.Maintenance.dtos.MaintenancesProposals.MaintenancesProposalsDto;
import com.example.demo01.domains.jpa.AssetManagement.Maintenance.services.MaintenanceWorkflow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/maintenance/workflow")
public class MaintenanceWorkFlowController {

    @Autowired
    MaintenanceWorkflow  maintenanceWorkflow;


    @PostMapping("/proposal/create")
    public ResponseEntity<?> createProposal(@RequestBody List<MaintenancesProposalRequest> requests){
        List<MaintenancesProposalsDto> results = maintenanceWorkflow.createProposals(requests);
        return ResponseEntity.ok().body(results);
    }

}
