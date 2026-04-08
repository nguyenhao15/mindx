package com.example.demo01.domains.HRManagment.Department.service.Impl;

import com.example.demo01.core.Exceptions.ResourceNotFoundException;
import com.example.demo01.domains.HRManagment.Department.dto.WorkingField.WorkingFieldDto;
import com.example.demo01.domains.HRManagment.Department.dto.WorkingField.WorkingFieldRequest;
import com.example.demo01.domains.HRManagment.Department.dto.WorkingField.WorkingFieldUpdate;
import com.example.demo01.domains.HRManagment.Department.mapper.WorkingFieldMapper;
import com.example.demo01.domains.HRManagment.Department.model.WorkingField;
import com.example.demo01.repository.mongo.HRManagement.departmentRepository.WorkingFieldModelRepository;
import com.example.demo01.domains.HRManagment.Department.service.WorkingFieldService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkingFieldServiceImpl implements WorkingFieldService {

    private final WorkingFieldModelRepository workingFieldModelRepository;

    private final WorkingFieldMapper workingFieldMapper;

    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    @PreAuthorize("hasRole('ADMIN') or hasRole('HR_MANAGER') or hasRole('HR_EMPLOYEE')")
    public WorkingField createWorkingField(WorkingFieldRequest request) {
        try {
            WorkingField workingField = workingFieldMapper.toWorkingFieldModel(request);
            return workingFieldModelRepository.save(workingField);
        } catch (DuplicateKeyException e) {
            throw new DuplicateKeyException(e.getMessage());
        }
    }

    @Override
    public List<WorkingFieldDto> getWorkingFieldByFieldCode(List<String> fieldCode) {
        List<WorkingField> workingField = workingFieldModelRepository.findByFieldCodeIn(fieldCode);
        if (workingField.isEmpty()) {
            throw new ResourceNotFoundException("WorkingField", "fieldCode", fieldCode.toString());
        }
        return workingFieldMapper.toWorkingFieldDtos(workingField);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') or hasRole('HR_MANAGER') or hasRole('HR_EMPLOYEE')")
    public List<WorkingField> getAllWorkingFields() {
        return workingFieldModelRepository.findAll();
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') or hasRole('HR_MANAGER') or hasRole('HR_EMPLOYEE')")
    public List<WorkingField> getActiveWorkingFields() {
        return workingFieldModelRepository.findByActive(true);
    }

    @Override
    public WorkingField getWorkingFieldById(String id) {
        return workingFieldModelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("WorkingField", "id", id));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') or hasRole('HR_MANAGER') or hasRole('HR_EMPLOYEE')")
    public WorkingField updateWorkingField(String id, WorkingFieldRequest request) {
        WorkingField workingField = getWorkingFieldById(id);
        workingFieldMapper.updateWorkingFieldModelFromRequest(request, workingField);
        WorkingField updatedWorkingField = workingFieldModelRepository.save(workingField);
        applicationEventPublisher.publishEvent(new WorkingFieldUpdate(
                updatedWorkingField.getId(),
                updatedWorkingField.getFieldCode(),
                updatedWorkingField.getFieldName(),
                updatedWorkingField.getActive()
        ));
        return updatedWorkingField;
    }

    @Override
    public void deleteWorkingField(String id) {
        WorkingField workingField = getWorkingFieldById(id);
        workingFieldModelRepository.delete(workingField);
    }
}
