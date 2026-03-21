package com.example.demo01.domains.HRManagment.Department.mapper;

import com.example.demo01.domains.HRManagment.Department.dto.WorkingField.WorkingFieldDto;
import com.example.demo01.domains.HRManagment.Department.dto.WorkingField.WorkingFieldRequest;
import com.example.demo01.domains.HRManagment.Department.model.WorkingField;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WorkingFieldMapper {

    WorkingField toWorkingFieldModel(WorkingFieldRequest request);

    List<WorkingFieldDto> toWorkingFieldDtos(List<WorkingField> workingFields);

    @BeanMapping(nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
    void updateWorkingFieldModelFromRequest(WorkingFieldRequest request,@MappingTarget WorkingField workingField);

}
