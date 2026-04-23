package com.example.demo01.domains.jpa.Core.Approval.service.impl;

import com.example.demo01.configs.SecureUtil.SecurityRepoUtil;
import com.example.demo01.core.Auth.dtos.CustomUserDetails;
import com.example.demo01.core.Exceptions.ResourceNotFoundException;
import com.example.demo01.domains.jpa.Core.Approval.dto.Approval.ApprovalPolicyInfoDto;
import com.example.demo01.domains.jpa.Core.Approval.dto.Approval.ApprovalPolicyRequestDto;
import com.example.demo01.domains.jpa.Core.Approval.entities.AllowTypeEnum;
import com.example.demo01.domains.jpa.Core.Approval.entities.ApprovalPolicyEntity;
import com.example.demo01.domains.jpa.Core.Approval.entities.WorkFlowTransitionEntity;
import com.example.demo01.domains.jpa.Core.Approval.mapper.ApprovalPolicyMapper;
import com.example.demo01.domains.jpa.Core.Approval.service.ApprovalPolicyService;
import com.example.demo01.domains.jpa.Core.Approval.service.WorkFlowTransitionService;
import com.example.demo01.repository.postgreSQL.Core.ApprovalRepository.ApprovalPolicyRepository;
import com.example.demo01.utils.*;
import com.example.demo01.utils.Query.PostgreSQL.DynamicSpecificationBuilder;
import com.example.demo01.utils.Query.PostgreSQL.PostgreSQLPageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class ApprovalPolicyServiceImpl implements ApprovalPolicyService {

    @Autowired
    private ApprovalPolicyRepository approvalPolicyRepository;

    @Autowired
    private ApprovalPolicyMapper approvalPolicyMapper;

    @Autowired
    private SecurityRepoUtil securityRepoUtil;

    @Autowired
    private DynamicSpecificationBuilder<ApprovalPolicyEntity> dynamicSpecificationBuilder;

    @Autowired
    private PostgreSQLPageUtil postgreSQLPageUtil;

    @Autowired
    private WorkFlowTransitionService workFlowTransitionService;

    @Override
    public ApprovalPolicyInfoDto createApprovalPolicy(ApprovalPolicyRequestDto requestDto) {
        Long workFlowId = requestDto.getWorkFlowId();
        String requesterPosition = requestDto.getRequesterPosition();
        if(requesterPosition == null || requesterPosition.isBlank()) {
            requestDto.setRequesterPosition("*");
        }
        WorkFlowTransitionEntity workFlowTransitionEntity = workFlowTransitionService.getWorkFlowTransitionById(workFlowId);
        ApprovalPolicyEntity approvalPolicyEntity = approvalPolicyMapper.fromRequestToEntity(requestDto);
        approvalPolicyEntity.setWorkflowAction(workFlowTransitionEntity);
        approvalPolicyEntity.setTargetStatus(workFlowTransitionEntity.getToStatus());
        ApprovalPolicyEntity savedEntity = approvalPolicyRepository.save(approvalPolicyEntity);
        return approvalPolicyMapper.fromEntityToInfo(savedEntity);
    }

    @Override
    public ApprovalPolicyEntity getApprovalPolicyById(Long id) {
        return approvalPolicyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ApprovalPolicyEntity", "id", id));
    }

    @Override
    public ApprovalPolicyInfoDto getApprovalPolicyDtoById(Long id) {
        ApprovalPolicyEntity savedEntity = getApprovalPolicyById(id);
        return approvalPolicyMapper.fromEntityToInfo(savedEntity);
    }

    @Override
    public List<ApprovalPolicyInfoDto> getRule(String currentStatus, ModuleEnum moduleEnum) {
        List<ApprovalPolicyEntity> approvalPolicyEntities = approvalPolicyRepository.findByTargetStatusAndModule(currentStatus, moduleEnum);
        return approvalPolicyMapper.fromEntityListToInfoList(approvalPolicyEntities);
    }

    @Override
    public Boolean getExactRule(String targetStatus, String from, ModuleEnum moduleEnum, String author) {
        CustomUserDetails currentUser = securityRepoUtil.getCurrentUserDetails();
        ApprovalPolicyEntity approvalPolicyEntity = approvalPolicyRepository.findByTargetStatusAndRequesterPositionAndModule(targetStatus, from, moduleEnum);

        if (approvalPolicyEntity == null) {
            approvalPolicyEntity = approvalPolicyRepository.findByTargetStatusAndRequesterPositionAndModule(targetStatus, "*", moduleEnum);
        }

        if (approvalPolicyEntity == null) {
            return false;
        }

        AllowTypeEnum allowType = approvalPolicyEntity.getAllowType();
        String allowTypeValue = approvalPolicyEntity.getAllowValue();


        switch (allowType) {
            case DEPARTMENT -> {
                String defaultDepartmentId = currentUser.getDepartmentId();
                return defaultDepartmentId.contains(allowTypeValue);
            }
            case POSITION -> {
                String position = currentUser.getPosition();
                return position.contains(allowTypeValue);
            }
            case AUTHOR -> {
                return Objects.equals(currentUser.getStaffId(), author);
            }
            default -> {
                return false;
            }
        }
    }

    @Override
    public BasePageResponse<ApprovalPolicyInfoDto> getAllApprovalPolicies(FilterWithPagination filter) {
        PageInput pageInput = filter.getPagination();
        List<FilterRequest> filters = filter.getFilters();
        Map<String, Specification<ApprovalPolicyEntity>> specificationMap = Map.of();

        Specification<ApprovalPolicyEntity> finalSpecification = dynamicSpecificationBuilder.build(filters, specificationMap);

        Page<ApprovalPolicyEntity> page = postgreSQLPageUtil.buildPageResponse(
                finalSpecification,
                pageInput,
                approvalPolicyRepository
        );
        return buildPageResponse(page);
    }

    public BasePageResponse<ApprovalPolicyInfoDto> buildPageResponse(Page<ApprovalPolicyEntity> page) {
        List<ApprovalPolicyEntity> content = page.getContent();
        BasePageResponse<ApprovalPolicyInfoDto> response = new BasePageResponse<>();
        response.setContent(approvalPolicyMapper.fromEntityListToInfoList(content));
        response.setPageNumber(page.getNumber());
        response.setPageSize(page.getSize());
        response.setTotalPages(page.getTotalPages());
        response.setTotalElements(page.getTotalElements());
        response.setLastPage(page.isLast());
        return response;
    }


    @Override
    public ApprovalPolicyInfoDto updateApprovalPolicy(Long id, ApprovalPolicyRequestDto requestDto) {
        ApprovalPolicyEntity approvalPolicyEntity = getApprovalPolicyById(id);

        approvalPolicyMapper.updateEntityFromRequest(requestDto, approvalPolicyEntity);
        ApprovalPolicyEntity savedEntity = approvalPolicyRepository.save(approvalPolicyEntity);
        return  approvalPolicyMapper.fromEntityToInfo(savedEntity) ;
    }

    @Override
    public void deleteApprovalPolicy(Long id) {
        ApprovalPolicyEntity approvalPolicyEntity = getApprovalPolicyById(id);
        approvalPolicyRepository.delete(approvalPolicyEntity);
    }
}
