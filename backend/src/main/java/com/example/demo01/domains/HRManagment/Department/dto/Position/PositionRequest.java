package com.example.demo01.domains.HRManagment.Department.dto.Position;

import com.example.demo01.domains.HRManagment.Department.dto.Department.DepartmentInfoDto;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
