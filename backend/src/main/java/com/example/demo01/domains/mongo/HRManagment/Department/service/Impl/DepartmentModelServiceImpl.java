package com.example.demo01.domains.mongo.HRManagment.Department.service.Impl;

import com.example.demo01.configs.SecureRepoConfig.SecurityRepoUtilImpl;
import com.example.demo01.core.Exceptions.ResourceNotFoundException;
import com.example.demo01.domains.mongo.HRManagment.Department.dto.Department.DepartmentInfoDto;
import com.example.demo01.domains.mongo.HRManagment.Department.dto.Department.DepartmentRequest;
import com.example.demo01.domains.mongo.HRManagment.Department.dto.WorkingField.WorkingFieldDto;
import com.example.demo01.domains.mongo.HRManagment.Department.mapper.DepartmentMapper;
import com.example.demo01.domains.mongo.HRManagment.Department.model.DepartmentModel;
import com.example.demo01.repository.mongo.HRManagement.departmentRepository.DepartmentModelRepository;
import com.example.demo01.domains.mongo.HRManagment.Department.service.DepartmentModelService;
import com.example.demo01.domains.mongo.HRManagment.Department.service.WorkingFieldService;
import com.example.demo01.utils.*;
import com.example.demo01.utils.Query.Mongo.DynamicQueryCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentModelServiceImpl implements DepartmentModelService {

    private final DepartmentModelRepository departmentModelRepository;

    private final DepartmentMapper departmentMapper;

    private final AppUtil  appUtil;

    private final DynamicQueryCriteria dynamicQueryCriteria;

    private final WorkingFieldService workingFieldService;

    private final SecurityRepoUtilImpl securityRepoUtil;

    @Override
    public DepartmentModel createDepartment(DepartmentRequest request) {
        List<String> workingFieldIds = request.getWorkingFields().stream()
                .map(WorkingFieldDto::getFieldCode)
                .toList();
        List<WorkingFieldDto> workingFields = workingFieldService.getWorkingFieldByFieldCode(workingFieldIds);
        String svgIcon = request.getIconSvg();
        if (svgIcon == null && svgIcon.trim().isEmpty()) {
            request.setIconSvg("data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIyNCIgaGVpZ2h0PSIyNCIgdmlld0JveD0iMCAwIDI0IDI0IiBmaWxsPSJub25lIiBzdHJva2U9ImN1cnJlbnRDb2xvciIgc3Ryb2tlLXdpZHRoPSIyIiBzdHJva2UtbGluZWNhcD0icm91bmQiIHN0cm9rZS1saW5lam9pbj0icm91bmQiIGNsYXNzPSJsdWNpZGUgbHVjaWRlLWJyaWVmY2FzZS1idXNpbmVzcy1pY29uIGx1Y2lkZS1icmllZmNhc2UtYnVzaW5lc3MiPjxwYXRoIGQ9Ik0xMiAxMmguMDEiLz48cGF0aCBkPSJNMTYgNlY0YTIgMiAwIDAgMC0yLTJoLTRhMiAyIDAgMCAwLTIgMnYyIi8+PHBhdGggZD0iTTIyIDEzYTE4LjE1IDE4LjE1IDAgMCAxLTIwIDAiLz48cmVjdCB3aWR0aD0iMjAiIGhlaWdodD0iMTQiIHg9IjIiIHk9IjYiIHJ4PSIyIi8+PC9zdmc+");
        }

        DepartmentModel departmentModel = departmentMapper.toDepartmentModel(request);
        departmentModel.setWorkingFields(workingFields);
        return departmentModelRepository.save(departmentModel);
    }

    @Override
    public DepartmentModel getDepartmentById(String id) {
        return departmentModelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department","_id" , id));
    }

    @Override
    public DepartmentInfoDto getDepartmentByDepartmentCode(String departmentCode) {
        DepartmentModel departmentModel = departmentModelRepository.findByDepartmentCode(departmentCode);
        return departmentMapper.toDepartmentInfoDto(departmentModel);
    }

    @Override
    public BasePageResponse<DepartmentInfoDto> getInterfaceDepartmentList(FilterWithPagination filter) {
        PageInput  pageInput = filter.getPagination();
        List<Criteria> criteria = new ArrayList<>();
        Criteria userCriteria = new Criteria().orOperator(
                Criteria.where("isSecurity").is(false),
                Criteria.where("departmentCode").in(securityRepoUtil.getCurrentDepartmentIds())
        );
        criteria.add(Criteria.where("active").is(true));
        criteria.add(userCriteria);
        List<FilterRequest> filterRequest = filter. getFilters();

        Page<DepartmentModel> departmentPage = dynamicQueryCriteria.buildPageResponse(filterRequest,criteria,pageInput,DepartmentModel.class);
        return buildPageResponse(departmentPage);
    }

    @Override
    public BasePageResponse<DepartmentInfoDto> buildPageResponse(Page<DepartmentModel> page) {
        List<DepartmentInfoDto> content = departmentMapper.toDepartmentInfoDtos(page.getContent());
        Pageable pageable = page.getPageable();
        return new BasePageResponse<>(
                content,
                pageable.getPageNumber(),
                pageable.getPageSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast()
        );
    }

    @Override
    public List<DepartmentModel> getCurrentWorkingDepartment() {
        List<String> departmentIds = securityRepoUtil.getCurrentDepartmentIds();
        return departmentModelRepository.findByDepartmentCodeIn(departmentIds);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') or hasRole('HR_MANAGER') or hasRole('HR_EMPLOYEE')")
    public BasePageResponse<DepartmentInfoDto> getAllDepartments(FilterWithPagination filter) {
        PageInput pageInput = filter.getPagination();
        List<FilterRequest> filterRequests = filter.getFilters();
        List<Criteria> criteria = new ArrayList<>();
        Page<DepartmentModel> departmentPage = dynamicQueryCriteria.buildPageResponse(filterRequests,criteria,pageInput,DepartmentModel.class);
        return buildPageResponse(departmentPage);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') or hasRole('HR_MANAGER') or hasRole('HR_EMPLOYEE')")
    public List<DepartmentModel> getActiveDepartments() {
        return departmentModelRepository.findByActive(true);
    }

    @Override
    public List<DepartmentModel> getCanAccessDepartments() {
        Boolean isGlobal = securityRepoUtil.isCurrentUserGlobalAdmin();
        if (isGlobal) {
            return getActiveDepartments();
        } else {
            return getCurrentWorkingDepartment();
        }
    }

    @Override
    public DepartmentModel updateDepartment(String id, DepartmentRequest request) {
        DepartmentModel departmentModel = getDepartmentById(id);
        departmentMapper.updateDepartmentModelFromRequest(request, departmentModel);
        return departmentModelRepository.save(departmentModel);
    }


    @Override
    @PreAuthorize("hasRole('ADMIN') or hasRole('HR_MANAGER')")
    public void deleteDepartment(String id) {
        DepartmentModel departmentModel = getDepartmentById(id);
        departmentModelRepository.delete(departmentModel);
    }
}
