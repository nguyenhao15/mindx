package com.example.demo01.domains.mongo.HRManagment.Department.service;

import com.example.demo01.domains.mongo.HRManagment.Department.dto.Department.DepartmentInfoDto;
import com.example.demo01.domains.mongo.HRManagment.Department.dto.Department.DepartmentRequest;
import com.example.demo01.domains.mongo.HRManagment.Department.model.DepartmentModel;
import com.example.demo01.utils.BasePageResponse;
import com.example.demo01.utils.FilterWithPagination;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DepartmentModelService {

    DepartmentModel createDepartment(DepartmentRequest request);

    DepartmentModel getDepartmentById(String id);

    DepartmentInfoDto getDepartmentByDepartmentCode(String departmentCode);

    BasePageResponse<DepartmentInfoDto> getInterfaceDepartmentList(FilterWithPagination filter);

    BasePageResponse<DepartmentInfoDto> buildPageResponse(Page<DepartmentModel> page);

    DepartmentModel getCurrentWorkingDepartment();

    BasePageResponse<DepartmentInfoDto> getAllDepartments(FilterWithPagination filter);

    List<DepartmentModel> getActiveDepartments();

    List<DepartmentModel> getCanAccessDepartments();

    DepartmentModel updateDepartment(String id, DepartmentRequest request);

    void deleteDepartment(String id);

}
