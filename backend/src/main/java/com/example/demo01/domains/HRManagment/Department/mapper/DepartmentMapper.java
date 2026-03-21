package com.example.demo01.domains.HRManagment.Department.mapper;

import com.example.demo01.domains.HRManagment.Department.dto.Department.DepartmentInfoDto;
import com.example.demo01.domains.HRManagment.Department.dto.Department.DepartmentRequest;
import com.example.demo01.domains.HRManagment.Department.model.DepartmentModel;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;


@Mapper(componentModel = "spring")
public interface DepartmentMapper {

    DepartmentModel toDepartmentModel(DepartmentRequest request);

    DepartmentInfoDto toDepartmentInfoDto(DepartmentModel departmentModel);

    List<DepartmentInfoDto> toDepartmentInfoDtos(List<DepartmentModel> departmentModels);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateDepartmentModelFromRequest(DepartmentRequest request,@MappingTarget DepartmentModel departmentModel);

}
