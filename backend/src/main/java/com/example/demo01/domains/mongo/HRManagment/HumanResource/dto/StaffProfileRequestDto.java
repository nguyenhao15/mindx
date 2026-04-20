package com.example.demo01.domains.mongo.HRManagment.HumanResource.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StaffProfileRequestDto {

    @NotBlank(message = "Staff Id is required")
    private String staffId;

    private String userId;

    @NotBlank(message = "Department Id is required")
    private String departmentId;

    @NotBlank(message = "Position Id is required")
    private String positionId;

    @NotBlank(message = "Department Name is required")
    private String departmentName;

    @NotBlank(message = "Position Name is required")
    private String positionName;

    @NotNull(message = "Position Level is required")
    private int positionLevel;

    private List<String> buAllowedList = new ArrayList<>();

    private Boolean isDefault;

    private Boolean active = true;
}
