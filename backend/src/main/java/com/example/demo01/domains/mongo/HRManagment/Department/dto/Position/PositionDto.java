package com.example.demo01.domains.mongo.HRManagment.Department.dto.Position;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
