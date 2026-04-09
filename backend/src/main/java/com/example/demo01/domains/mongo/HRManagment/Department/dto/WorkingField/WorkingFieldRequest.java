package com.example.demo01.domains.mongo.HRManagment.Department.dto.WorkingField;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WorkingFieldRequest {

    @NotBlank
    private String fieldName;

    @NotBlank
    private String fieldCode;

    private Boolean active;

    private String description;
}
