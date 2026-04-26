package com.example.demo01.domains.jpa.Core.Approval.dto.WorkFlowTransition;

import com.example.demo01.domains.jpa.Core.Approval.dto.Approval.ApprovalPolicyInfoDto;
import com.example.demo01.utils.ModuleEnum;

import java.time.Instant;
import java.util.List;

public record WorkFlowTransitionInfoDto (

     Long id,
     String fromStatus,
     String toStatus,
     String description,
     ModuleEnum module,

     String labelName,

     String operator,

     String actionType,

     List<ApprovalPolicyInfoDto> approvalPolicyEntity,

     Boolean enabled,

     String createdBy,
     Instant createdDate,
     String lastModifiedBy,
     Instant lastModifiedDate
){}
