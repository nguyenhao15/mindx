package com.example.demo01.domains.mongo.HRManagment.Department.controller;

import com.example.demo01.domains.mongo.HRManagment.Department.dto.Department.DepartmentRequest;
import com.example.demo01.domains.mongo.HRManagment.Department.service.DepartmentModelService;
import com.example.demo01.utils.FilterWithPagination;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentModelService departmentModelService;

    @PostMapping
    public ResponseEntity<?> createDepartment(@Valid @RequestBody DepartmentRequest request) {
        return ResponseEntity.ok(departmentModelService.createDepartment(request));
    }

    @PostMapping("/all-departments")
    @PreAuthorize("hasRole('ADMIN') or hasRole('HR_MANAGER') or hasRole('HR_EMPLOYEE')")
    public ResponseEntity<?> getAllDepartments(@RequestBody FilterWithPagination filter ) {
        return ResponseEntity.ok(departmentModelService.getAllDepartments(filter));
    }

    @GetMapping("/current-working-department")
    public ResponseEntity<?> getCurrentWorkingDepartment() {
        return ResponseEntity.ok(departmentModelService.getCurrentWorkingDepartment());
    }

    @PostMapping("/insecure-departments")
    public ResponseEntity<?> getPublicDepartments(@RequestBody FilterWithPagination filter) {
        return ResponseEntity.ok(departmentModelService.getInterfaceDepartmentList(filter));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDepartmentById(@PathVariable String id) {
        return ResponseEntity.ok(departmentModelService.getDepartmentById(id));
    }

    @GetMapping("/active-departments")
    public ResponseEntity<?> getActiveDepartments() {
        return ResponseEntity.ok(departmentModelService.getActiveDepartments());
    }

    @GetMapping("/can-access-departments")
    public ResponseEntity<?> getCanAccessDepartments() {
        return ResponseEntity.ok(departmentModelService.getCanAccessDepartments());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateDepartment(@PathVariable String id, @Valid @RequestBody DepartmentRequest request) {
        return ResponseEntity.ok(departmentModelService.updateDepartment(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDepartment(@PathVariable String id) {
        departmentModelService.deleteDepartment(id);
        return ResponseEntity.noContent().build();
    }
}
