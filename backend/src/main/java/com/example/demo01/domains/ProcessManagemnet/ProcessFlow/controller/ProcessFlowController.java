package com.example.demo01.domains.ProcessManagemnet.ProcessFlow.controller;

import com.example.demo01.domains.ProcessManagemnet.ProcessFlow.dtos.ProcessFlow.ProcessFlowRequest;
import com.example.demo01.domains.ProcessManagemnet.ProcessFlow.service.ProcessFlowService;
import com.example.demo01.utils.FilterWithPagination;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/process/process-flows")
public class ProcessFlowController {

    private final ProcessFlowService processFlowService;

    @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> createProcessFlow(@RequestPart(value = "data") @Valid ProcessFlowRequest processFlowRequest,
                                               @RequestPart(value = "files") List<MultipartFile> files) {
        return ResponseEntity.ok(processFlowService.createProcessFlow(processFlowRequest, files));
    }

    @PostMapping("/all")
    public ResponseEntity<?> getAllProcessFlows(@RequestBody FilterWithPagination filter) {
        return ResponseEntity.ok(processFlowService.getAllProcessFlows(filter));
    }

    @PostMapping("/by-department/{departmentId}")
    public ResponseEntity<?> getProcessFlowByDepartmentId(@PathVariable String departmentId,
                                                          @RequestBody FilterWithPagination filter) {
        return ResponseEntity.ok(processFlowService.getProcessFlowByDepartmentId(departmentId, filter));
    }

    @PostMapping("/by-processing")
    public ResponseEntity<?> getProcessFlowByProcessing(@RequestBody FilterWithPagination filter) {
        return ResponseEntity.ok(processFlowService.getProcessWithProcessing(filter));
    }

    @GetMapping("/search-documents")
    public ResponseEntity<?> searchProcessFlow(@RequestParam String keyword) {
        return ResponseEntity.ok(processFlowService.searchFlowItem(keyword));
    }

    @PostMapping("/active/{active}")
    public ResponseEntity<?> getActiveProcessFlow(@PathVariable Boolean active,
                                                  @RequestBody FilterWithPagination filter) {
        return ResponseEntity.ok(processFlowService.getActiveProcessFlow(active, filter));
    }

    @PatchMapping("/activate-document/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('EDITOR')")
    public ResponseEntity<?> activateProcessFlow(@PathVariable String id) {
        return ResponseEntity.ok(processFlowService.activateProcessFlow(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProcessFlow(@PathVariable String id) {
        return ResponseEntity.ok(processFlowService.deleteProcessFlow(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProcessFlowById(@PathVariable String id) {
        return ResponseEntity.ok(processFlowService.getProcessFlowDtoById(id));
    }

    @GetMapping("/{id}/full-info")
    public ResponseEntity<?> getProcessFullInfoById(@PathVariable String id) {
        return ResponseEntity.ok(processFlowService.getProcessFlowFullInfoById(id));
    }

    @PatchMapping(value = "/{id}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> updateProcessFlow(@PathVariable String id,
                                               @RequestPart(value = "data") @Valid ProcessFlowRequest processFlowRequest,
                                               @RequestPart(value = "files", required = false) List<MultipartFile> files) {
        return ResponseEntity.ok(processFlowService.updateProcessFlowById(id, processFlowRequest, files));
    }

}
