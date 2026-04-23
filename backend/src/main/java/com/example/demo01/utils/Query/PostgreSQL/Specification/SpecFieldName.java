package com.example.demo01.utils.Query.PostgreSQL.Specification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SpecFieldName {
    private String basementName;
    private String departmentName;
    private String positionName;
    private String workingFieldName;
    private String assignedFieldName;
}
