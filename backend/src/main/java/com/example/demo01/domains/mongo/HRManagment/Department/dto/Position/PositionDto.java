package com.example.demo01.domains.mongo.HRManagment.Department.dto.Position;

import com.example.demo01.utils.ScopeView;
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

    private ScopeView scopeView;

    private int positionLevel;

    private Boolean active;
}
