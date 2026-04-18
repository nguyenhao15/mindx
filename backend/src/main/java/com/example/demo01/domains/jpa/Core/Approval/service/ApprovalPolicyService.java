package com.example.demo01.domains.jpa.Core.Approval.service;

import com.example.demo01.domains.jpa.Core.Approval.dto.Approval.ApprovalPolicyInfoDto;
import com.example.demo01.domains.jpa.Core.Approval.dto.Approval.ApprovalPolicyRequestDto;
import com.example.demo01.domains.jpa.Core.Approval.entities.ApprovalPolicyEntity;
import com.example.demo01.utils.BasePageResponse;
import com.example.demo01.utils.FilterWithPagination;
import com.example.demo01.utils.ModuleEnum;

import java.util.List;

public interface ApprovalPolicyService {

    ApprovalPolicyInfoDto createApprovalPolicy(ApprovalPolicyRequestDto requestDto);

    ApprovalPolicyEntity getApprovalPolicyById(Long id);

    ApprovalPolicyInfoDto getApprovalPolicyDtoById(Long id);

    List<ApprovalPolicyInfoDto> getRule(String targetStatus, ModuleEnum moduleEnum);

    Boolean getExactRule(String targetStatus,String from , ModuleEnum moduleEnum);

    BasePageResponse<ApprovalPolicyInfoDto> getAllApprovalPolicies(FilterWithPagination filter);

    ApprovalPolicyInfoDto updateApprovalPolicy(Long id, ApprovalPolicyRequestDto requestDto);
    void deleteApprovalPolicy(Long id);

}
