package com.example.demo01.domains.mongo.HRManagment.Department.service;

import com.example.demo01.domains.mongo.HRManagment.Department.dto.WorkingField.WorkingFieldDto;
import com.example.demo01.domains.mongo.HRManagment.Department.dto.WorkingField.WorkingFieldRequest;
import com.example.demo01.domains.mongo.HRManagment.Department.model.WorkingField;

import java.util.List;

public interface WorkingFieldService {

    WorkingField createWorkingField(WorkingFieldRequest request);

    List<WorkingFieldDto> getWorkingFieldByFieldCode(List<String> fieldCode);

    List<WorkingField> getAllWorkingFields();

    List<WorkingField> getActiveWorkingFields();

    WorkingField getWorkingFieldById(String id);

    WorkingField updateWorkingField(String id, WorkingFieldRequest request);

    void deleteWorkingField(String id);

}
