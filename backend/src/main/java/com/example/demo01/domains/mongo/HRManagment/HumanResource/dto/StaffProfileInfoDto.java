package com.example.demo01.domains.mongo.HRManagment.HumanResource.dto;

import java.util.List;

public record StaffProfileInfoDto(
        String id,
        String staffId,
        String userId,
        String departmentId,
        String positionId,
        String departmentName,
        String positionName,
        int positionLevel,
        List<String> buAllowedList
) {
}
