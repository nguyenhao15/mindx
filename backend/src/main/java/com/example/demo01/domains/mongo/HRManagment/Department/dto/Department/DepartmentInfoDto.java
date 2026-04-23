package com.example.demo01.domains.mongo.HRManagment.Department.dto.Department;

import com.example.demo01.domains.mongo.HRManagment.Department.dto.WorkingField.WorkingFieldDto;
import com.example.demo01.utils.ScopeView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DepartmentInfoDto {

    private String id;
    private String departmentName;

    private String departmentCode;

    private String description;

    private String iconSvg;

    private Boolean isSecurity;

    private ScopeView scopeView;

    private Boolean active;

    private List<WorkingFieldDto> workingFields;

}
