package com.example.demo01.domains.HRManagment.Department.controller;

import com.example.demo01.domains.HRManagment.Department.dto.WorkingField.WorkingFieldRequest;
import com.example.demo01.domains.HRManagment.Department.service.WorkingFieldService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/working-fields")
@RequiredArgsConstructor
public class WorkingFieldController {

    private final WorkingFieldService workingFieldService;

    @PostMapping
    public ResponseEntity<?> createWorkingField(@RequestBody @Valid WorkingFieldRequest request) {
        return ResponseEntity.ok(workingFieldService.createWorkingField(request));
    }

    @GetMapping
    public ResponseEntity<?> getAllWorkingFields() {
        return ResponseEntity.ok(workingFieldService.getAllWorkingFields());
    }

    @GetMapping("/active")
    public ResponseEntity<?> getActiveWorkingFields() {
        return ResponseEntity.ok(workingFieldService.getActiveWorkingFields());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateWorkingField(@PathVariable String id, @RequestBody @Valid WorkingFieldRequest request) {
        return ResponseEntity.ok(workingFieldService.updateWorkingField(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteWorkingField(@PathVariable String id) {
        workingFieldService.deleteWorkingField(id);
        return ResponseEntity.noContent().build();
    }

}
