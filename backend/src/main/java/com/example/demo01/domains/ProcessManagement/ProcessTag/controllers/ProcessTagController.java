package com.example.demo01.domains.ProcessManagement.ProcessTag.controllers;

import com.example.demo01.domains.ProcessManagement.ProcessTag.dtos.processTag.ProcessTagRequest;
import com.example.demo01.domains.ProcessManagement.ProcessTag.service.ProcessTagService;
import com.example.demo01.utils.FilterWithPagination;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/process/process-tags")
@RequiredArgsConstructor
public class ProcessTagController {

    private final ProcessTagService processTagService;

    @PostMapping
    public ResponseEntity<?> createProcessTag(@Valid @RequestBody ProcessTagRequest request) {
        return ResponseEntity.ok(processTagService.createProcessTag(request));
    }

    @GetMapping("/active/{active}")
    public ResponseEntity<?> getProcessTag() {
        return ResponseEntity.ok(processTagService.getActiveProcessTagOptions());
    }

    @PostMapping("/get-all")
    public ResponseEntity<?> getAllProcessTag(@RequestBody FilterWithPagination filter) {
        return ResponseEntity.ok(processTagService.getAllProcessTags(filter));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateProcessTag(@PathVariable String id, @Valid @RequestBody ProcessTagRequest request) {
        return ResponseEntity.ok(processTagService.updateProcessTag(id, request));
    }

}
