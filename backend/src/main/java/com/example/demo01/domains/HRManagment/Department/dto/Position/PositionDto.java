package com.example.demo01.domains.HRManagment.Department.dto.Position;

import com.example.demo01.domains.HRManagment.Department.dto.Department.DepartmentInfoDto;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PositionDto {


    private String id;

    private String positionName;

    private String positionCode;

    private String description;
    private String departmentCode;


    private int positionLevel;

    private Boolean active;
}
