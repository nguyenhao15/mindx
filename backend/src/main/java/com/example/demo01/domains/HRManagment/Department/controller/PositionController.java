package com.example.demo01.domains.HRManagment.Department.controller;

import com.example.demo01.domains.HRManagment.Department.dto.Position.PositionRequest;
import com.example.demo01.domains.HRManagment.Department.service.PositionModelService;
import com.example.demo01.utils.FilterWithPagination;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/positions")
@RequiredArgsConstructor
public class PositionController {

    private final PositionModelService positionModelService;

    @PostMapping
    public ResponseEntity<?> createPosition(@RequestBody @Valid PositionRequest request) {
        return ResponseEntity.ok(positionModelService.createPosition(request));
    }

    @PostMapping("/get-all")
    public ResponseEntity<?> getAllPositions(@RequestBody @Valid FilterWithPagination request) {
        return ResponseEntity.ok(positionModelService.getAllPositions(request));
    }

    @GetMapping("active/{active}")
    public ResponseEntity<?> getPositionsByActive(@PathVariable boolean active) {
        return ResponseEntity.ok(positionModelService.getPositionByActive(active));
    }

    @GetMapping("/department/{departmentId}")
    public ResponseEntity<?> getPositionsByDepartmentId(@PathVariable String departmentId) {
        return ResponseEntity.ok(positionModelService.getPositionsByDepartmentId(departmentId));
    }

    @GetMapping("/current")
    public ResponseEntity<?> getCurrentWorkingPosition() {
        return ResponseEntity.ok(positionModelService.getCurrentWorkingPosition());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPositionById(@PathVariable String id) {
        return ResponseEntity.ok(positionModelService.getPositionById(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updatePosition(@PathVariable String id,
                                            @RequestBody @Valid PositionRequest request) {
        return ResponseEntity.ok(positionModelService.updatePosition(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePosition(@PathVariable String id) {
        positionModelService.deletePosition(id);
        return ResponseEntity.noContent().build();
    }

}
