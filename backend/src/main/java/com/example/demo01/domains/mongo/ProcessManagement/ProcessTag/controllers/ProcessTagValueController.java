package com.example.demo01.domains.mongo.ProcessManagement.ProcessTag.controllers;

import com.example.demo01.domains.mongo.ProcessManagement.ProcessTag.dtos.processValue.ProcessTagValueRequest;
import com.example.demo01.domains.mongo.ProcessManagement.ProcessTag.service.ProcessTagValueService;
import com.example.demo01.utils.FilterWithPagination;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/process/process-tag-values")
public class ProcessTagValueController {

    private final ProcessTagValueService processTagValueService;

    @PostMapping
    public ResponseEntity<?> createProcessTagValue(@Valid @RequestBody ProcessTagValueRequest request) {
        return ResponseEntity.ok(processTagValueService.createTagValue(request));

    }

    @GetMapping("/active-options")
    public ResponseEntity<?> getProcessTagValueOptions() {
        return ResponseEntity.ok(processTagValueService.getProcessTagValueOptions());
    }

    @PostMapping("/get-all")
    public ResponseEntity<?> getAllProcessTagValues(@RequestBody FilterWithPagination filterWithPagination) {
        return ResponseEntity.ok(processTagValueService.getAllTagValues(filterWithPagination));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateProcessTagValue(@PathVariable String id,
                                                   @Valid @RequestBody ProcessTagValueRequest request) {
        return ResponseEntity.ok(processTagValueService.updateTagValue(id,request));
    }

}
