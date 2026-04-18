package com.example.demo01.domains.jpa.Core.Approval.service.impl;

import com.example.demo01.domains.jpa.Core.Approval.dto.Approval.ApprovalPolicyInfoDto;
import com.example.demo01.domains.jpa.Core.Approval.dto.Approval.ApprovalPolicyRequestDto;

public interface ApprovalPolicyService {

    ApprovalPolicyInfoDto createApprovalPolicy(ApprovalPolicyRequestDto requestDto);
    ApprovalPolicyInfoDto updateApprovalPolicy(ApprovalPolicyRequestDto requestDto);
    void deleteApprovalPolicy(Long id);

}
