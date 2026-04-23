package com.example.demo01.domains.mongo.HRManagment.HumanResource.controller;

import com.example.demo01.domains.mongo.HRManagment.HumanResource.dto.StaffProfileInfoDto;
import com.example.demo01.domains.mongo.HRManagment.HumanResource.dto.StaffProfileRequestDto;
import com.example.demo01.domains.mongo.HRManagment.HumanResource.service.StaffProfileService;
import com.example.demo01.utils.FilterWithPagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/staff-profile")
public class StaffProfileController {

    @Autowired
    private StaffProfileService staffProfileService;

    @GetMapping("/department/{departmentId}")
    public ResponseEntity<?> getStaffProfileByDepartmentId(@PathVariable String departmentId) {
        List<StaffProfileInfoDto> staffProfileInfoDtos = staffProfileService.getStaffProfileByDepartmentId(departmentId);
        return ResponseEntity.ok(staffProfileInfoDtos);
    }

    @PostMapping("/get/page")
    public ResponseEntity<?> getStaffProfileWithPagination(@RequestBody FilterWithPagination filter) {
        return ResponseEntity.ok(staffProfileService.getStaffInfoList(filter));
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN') or hashRole('HR_MANAGER')")
    public ResponseEntity<?> updateStaffProfile(@PathVariable String id,
                                                @RequestBody StaffProfileRequestDto updateRequest) {
        StaffProfileInfoDto staffProfileInfoDto = staffProfileService.updateStaffProfileInfoById(id, updateRequest);
        return ResponseEntity.ok(staffProfileInfoDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hashRole('HR_MANAGER')")
    public ResponseEntity<?> deleteStaffProfile(@PathVariable String id) {
        staffProfileService.terminateStaffProfile(id);
        return ResponseEntity.ok().build();
    }

}
