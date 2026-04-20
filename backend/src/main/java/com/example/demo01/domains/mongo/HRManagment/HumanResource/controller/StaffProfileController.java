package com.example.demo01.domains.mongo.HRManagment.HumanResource.controller;

import com.example.demo01.domains.mongo.HRManagment.HumanResource.dto.StaffProfileInfoDto;
import com.example.demo01.domains.mongo.HRManagment.HumanResource.dto.StaffProfileRequestDto;
import com.example.demo01.domains.mongo.HRManagment.HumanResource.service.StaffProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/staff-profile")
@PreAuthorize("hasRole('ADMIN') or hashRole('HR_MANAGER')")
public class StaffProfileController {

    @Autowired
    private StaffProfileService staffProfileService;

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateStaffProfile(@PathVariable String id,
                                                @RequestBody StaffProfileRequestDto updateRequest) {
        StaffProfileInfoDto staffProfileInfoDto = staffProfileService.updateStaffProfileInfoById(id, updateRequest);
        return ResponseEntity.ok(staffProfileInfoDto);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStaffProfile(@PathVariable String id) {
        staffProfileService.terminateStaffProfile(id);
        return ResponseEntity.ok().build();
    }

}
