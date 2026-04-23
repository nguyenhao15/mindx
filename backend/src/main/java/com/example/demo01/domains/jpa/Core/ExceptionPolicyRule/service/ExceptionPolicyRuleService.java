package com.example.demo01.domains.jpa.Core.ExceptionPolicyRule.service;

import com.example.demo01.domains.jpa.Core.ExceptionPolicyRule.dtos.ExceptionPolicyRuleInfoDto;
import com.example.demo01.domains.jpa.Core.ExceptionPolicyRule.dtos.ExceptionPolicyRuleRequestDto;
import com.example.demo01.domains.jpa.Core.ExceptionPolicyRule.entity.ExceptionPolicyRuleEntity;
import com.example.demo01.utils.BasePageResponse;
import com.example.demo01.utils.FilterWithPagination;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ExceptionPolicyRuleService {

    ExceptionPolicyRuleInfoDto createNewPolicyRule(ExceptionPolicyRuleRequestDto requestDto);

    ExceptionPolicyRuleInfoDto updatePolicyRule(Long id, ExceptionPolicyRuleRequestDto policyRuleInfoDto);

    List<ExceptionPolicyRuleInfoDto> getPolicyRuleInfoByUserId(String userId);

    ExceptionPolicyRuleEntity getPolicyRule(Long id);

    ExceptionPolicyRuleInfoDto getPolicyRuleInfo(Long id);

    BasePageResponse<ExceptionPolicyRuleInfoDto> getAllPolicyRules(FilterWithPagination filterWithPagination);

    BasePageResponse<ExceptionPolicyRuleInfoDto> buildPageResponse(Page<ExceptionPolicyRuleEntity> page);

    void deletePolicyRule(Long id);

}
