package com.example.demo01.domains.mongo.HRManagment.Department.dto.WorkingField;

public record WorkingFieldUpdate (
        String id,
        String fieldCode,
        String fieldName,
        Boolean active
) {}
