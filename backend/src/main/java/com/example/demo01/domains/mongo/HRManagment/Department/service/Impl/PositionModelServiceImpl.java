package com.example.demo01.domains.mongo.HRManagment.Department.service.Impl;

import com.example.demo01.configs.SecureRepoConfig.SecurityRepoUtilImpl;
import com.example.demo01.core.Exceptions.ResourceNotFoundException;
import com.example.demo01.domains.mongo.HRManagment.Department.dto.Position.PositionDto;
import com.example.demo01.domains.mongo.HRManagment.Department.dto.Position.PositionRequest;
import com.example.demo01.domains.mongo.HRManagment.Department.mapper.PositionMapper;
import com.example.demo01.domains.mongo.HRManagment.Department.model.PositionModel;
import com.example.demo01.repository.mongo.HRManagement.departmentRepository.PositionModelRepository;
import com.example.demo01.domains.mongo.HRManagment.Department.service.DepartmentModelService;
import com.example.demo01.domains.mongo.HRManagment.Department.service.PositionModelService;
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
public class PositionModelServiceImpl implements PositionModelService {

    private final PositionModelRepository positionModelRepository;

    private final DepartmentModelService departmentModelService;

    private final PositionMapper positionMapper;

    private final AppUtil appUtil;

    private final DynamicQueryCriteria dynamicQueryCriteria;

    private final SecurityRepoUtilImpl securityRepoUtil;

    @Override
    @PreAuthorize("hasRole('ADMIN') or hasRole('HR_MANAGER') or hasRole('HR_EMPLOYEE')")
    public PositionModel createPosition(PositionRequest request) {
        departmentModelService.getDepartmentByDepartmentCode(request.getDepartmentCode());
        PositionModel positionModel = positionMapper.toPositionModel(request);
        return positionModelRepository.save(positionModel);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') or hasRole('HR_MANAGER') or hasRole('HR_EMPLOYEE')")
    public BasePageResponse<PositionDto> getAllPositions(FilterWithPagination filter) {
        List<FilterRequest>  filters = filter.getFilters();
        PageInput  pageInput = filter.getPagination();
        List<Criteria> criteria= new ArrayList<>();

        Page<PositionModel> positionModels = dynamicQueryCriteria.buildPageResponse(filters, criteria, pageInput, PositionModel.class);
        return buildPageResponse(positionModels);
    }

    @Override
    public BasePageResponse<PositionDto> buildPageResponse(Page<PositionModel> positionModels) {
        List<PositionModel> content = positionModels.getContent();
        List<PositionDto> positionDtos = positionMapper.toDtoPositionList(content);
        Pageable p = positionModels.getPageable();

        return new BasePageResponse<>(
                positionDtos,
                p.getPageNumber(),
                p.getPageSize(),
                positionModels.getTotalElements(),
                positionModels.getTotalPages(),
                positionModels.isLast()
        );
    }

    @Override
    public List<PositionModel> getPositionByActive(Boolean isActive) {
        return positionModelRepository.findByActive(isActive);
    }

    @Override
    public List<PositionModel> getPositionsByDepartmentId(String departmentId) {
        return positionModelRepository.findByDepartmentCode(departmentId);
    }

    @Override
    public List<PositionModel> getCurrentWorkingPosition() {
        List<String> currentPositionIds = securityRepoUtil.getCurrentPositionIds();
        return positionModelRepository.findByIdIn(currentPositionIds);
    }

    @Override
    public PositionModel getPositionById(String id) {
       return positionModelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PositionModel", "id", id));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') or hasRole('HR_MANAGER') or hasRole('HR_EMPLOYEE')")
    public PositionModel updatePosition(String id, PositionRequest request) {
        PositionModel positionModel = getPositionById(id);
        positionMapper.updatePositionModelFromRequest(request, positionModel);
        return positionModelRepository.save(positionModel);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') or hasRole('HR_MANAGER')")
    public void deletePosition(String id) {
        PositionModel positionModel = getPositionById(id);
        positionModelRepository.delete(positionModel);
    }
}
