package com.example.demo01.domains.jpa.Core.Approval.controller;

import com.example.demo01.domains.jpa.Core.Approval.dto.WorkFlowTransition.WorkFlowTransitionInfoDto;
import com.example.demo01.domains.jpa.Core.Approval.dto.WorkFlowTransition.WorkFlowTransitionRequestDto;
import com.example.demo01.domains.jpa.Core.Approval.service.WorkFlowTransitionService;
import com.example.demo01.utils.FilterWithPagination;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/approval/work")
public class WorkFlowTransitionController {

    @Autowired
    private WorkFlowTransitionService service;

    @PostMapping
    public ResponseEntity<?> createNewWorkFlow(@Valid @RequestBody WorkFlowTransitionRequestDto  requestDto) {
        WorkFlowTransitionInfoDto workFlowTransitionInfoDto = service.createWorkFlowTransition(requestDto);
        return ResponseEntity.ok(workFlowTransitionInfoDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateWorkFlow(@PathVariable Long id, WorkFlowTransitionRequestDto requestDto) {
        WorkFlowTransitionInfoDto workFlowTransitionInfoDto = service.updateWorkFlowTransition(id, requestDto);
        return ResponseEntity.ok(workFlowTransitionInfoDto);
    }

    @PostMapping("/get/page")
    public ResponseEntity<?> getWorkFlowTransitionPage(@Valid @RequestBody FilterWithPagination filter) {
        return ResponseEntity.ok(service.getWorkFlowTransitionDtoByPage(filter));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getWorkFlowTransitionById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getWorkFlowTransitionDtoById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteWorkFlowTransitionById(@PathVariable Long id) {
        service.deleteWorkFlowTransition(id);
        return ResponseEntity.ok().build();
    }

}
