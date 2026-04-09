package com.example.demo01.domains.mongo.HRManagment.Department.dto.Position;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PositionRequest {

    @NotBlank
    private String positionName;

    @NotBlank
    private String positionCode;

    @NotBlank
    private String departmentCode;

    private String description;

    private int positionLevel;

    private Boolean active;

}
