package com.example.demo01.domains.jpa.Core.Approval.service.impl;

import com.example.demo01.core.Exceptions.ResourceNotFoundException;
import com.example.demo01.domains.jpa.Core.Approval.dto.WorkFlowTransition.WorkFlowTransitionInfoDto;
import com.example.demo01.domains.jpa.Core.Approval.dto.WorkFlowTransition.WorkFlowTransitionRequestDto;
import com.example.demo01.domains.jpa.Core.Approval.entities.WorkFlowTransitionEntity;
import com.example.demo01.domains.jpa.Core.Approval.mapper.WorkFlowTransitionMapper;
import com.example.demo01.domains.jpa.Core.Approval.service.WorkFlowTransitionService;
import com.example.demo01.repository.postgreSQL.Core.ApprovalRepository.WorkFlowTransitionRepository;
import com.example.demo01.utils.*;
import com.example.demo01.utils.Query.PostgreSQL.DynamicSpecificationBuilder;
import com.example.demo01.utils.Query.PostgreSQL.PostgreSQLPageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class WorkFlowTransitionServiceImpl implements WorkFlowTransitionService {

    @Autowired
    private WorkFlowTransitionRepository workFlowTransitionRepository;

    @Autowired
    private WorkFlowTransitionMapper workFlowTransitionMapper;

    @Autowired
    private DynamicSpecificationBuilder<WorkFlowTransitionEntity> dynamicSpecificationBuilder;

    @Autowired
    private PostgreSQLPageUtil postgreSQLPageUtil;

    @Override
    public WorkFlowTransitionInfoDto createWorkFlowTransition(WorkFlowTransitionRequestDto requestDto) {
        WorkFlowTransitionEntity workFlowTransitionEntity = workFlowTransitionMapper.fromRequestToEntity(requestDto);
        WorkFlowTransitionEntity savedEntity = workFlowTransitionRepository.save(workFlowTransitionEntity);
        return workFlowTransitionMapper.fromEntityToInfo(savedEntity);
    }

    @Override
    public WorkFlowTransitionEntity getWorkFlowTransitionById(Long id) {
        return workFlowTransitionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("WorkFlowEntity", "id", id));
    }

    @Override
    public WorkFlowTransitionInfoDto getWorkFlowTransitionDtoById(Long id) {
        WorkFlowTransitionEntity workFlowTransitionEntity = getWorkFlowTransitionById(id);
        return workFlowTransitionMapper.fromEntityToInfo(workFlowTransitionEntity);
    }

    @Override
    public List<WorkFlowTransitionInfoDto> getWorkFlowTransitionDtoByCurrentStatusAndModule(String currentStatus, ModuleEnum moduleEnum) {
        List<WorkFlowTransitionEntity> workFlowTransitionEntities = workFlowTransitionRepository.findByFromStatusAndModule(currentStatus, moduleEnum);
        if (workFlowTransitionEntities.isEmpty()) {
            return List.of();
        }
        return workFlowTransitionMapper.fromEntityListToInfoList(workFlowTransitionEntities);
    }

    @Override
    public BasePageResponse<WorkFlowTransitionInfoDto> getAllPageWorkFlowTransitionDto(FilterWithPagination filter) {
        PageInput  pageInput = filter.getPagination();
        List<FilterRequest> filters = filter.getFilters();
        Map<String, Specification<WorkFlowTransitionEntity>> specificationMap = Map.of();

        Specification<WorkFlowTransitionEntity> finalSpecification = dynamicSpecificationBuilder.build(filters, specificationMap);

        Page<WorkFlowTransitionEntity> page = postgreSQLPageUtil.buildPageResponse(
                finalSpecification,
                pageInput,
                workFlowTransitionRepository
        );

        return buildPageResponse(page);
    }

    @Override
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MANAGER') or hasAuthority('ROLE_USER')")
    public BasePageResponse<WorkFlowTransitionInfoDto> getWorkFlowTransitionDtoByPage(FilterWithPagination filter) {
        PageInput pageInput = filter.getPagination();

        List<FilterRequest> filters = filter.getFilters();
        Map<String, Specification<WorkFlowTransitionEntity>> specificationMap = Map.of();

        Specification<WorkFlowTransitionEntity> finalSpecification = dynamicSpecificationBuilder.build(filters, specificationMap);

        Page<WorkFlowTransitionEntity> page = postgreSQLPageUtil.buildPageResponse(
                finalSpecification,
                pageInput,
                workFlowTransitionRepository
        );

        return buildPageResponse(page);
    }

    @Override
    public BasePageResponse<WorkFlowTransitionInfoDto> buildPageResponse(Page<WorkFlowTransitionEntity> page) {
        List<WorkFlowTransitionEntity> content = page.getContent();
        BasePageResponse<WorkFlowTransitionInfoDto> response = new BasePageResponse<>();
        response.setContent(workFlowTransitionMapper.fromEntityListToInfoList(content));
        response.setPageNumber(page.getNumber());
        response.setPageSize(page.getSize());
        response.setTotalPages(page.getTotalPages());
        response.setTotalElements(page.getTotalElements());
        response.setLastPage(page.isLast());
        return response;
    }

    @Override
    public WorkFlowTransitionInfoDto updateWorkFlowTransition(Long id, WorkFlowTransitionRequestDto workFlowTransition) {
        WorkFlowTransitionEntity workFlowTransitionEntity  = getWorkFlowTransitionById(id);
        workFlowTransitionMapper.updateEntityFromRequest(workFlowTransition, workFlowTransitionEntity);
        WorkFlowTransitionEntity updated = workFlowTransitionRepository.save(workFlowTransitionEntity);
        return workFlowTransitionMapper.fromEntityToInfo(updated);
    }

    @Override
    public void deleteWorkFlowTransition(Long id) {
        WorkFlowTransitionEntity workFlowTransitionEntity = getWorkFlowTransitionById(id);
        workFlowTransitionRepository.delete(workFlowTransitionEntity);
    }
}
