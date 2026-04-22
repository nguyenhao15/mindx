package com.example.demo01.domains.jpa.Core.Approval.dto.WorkFlowTransition;

import com.example.demo01.domains.jpa.Core.Approval.dto.Approval.ApprovalPolicyInfoDto;
import com.example.demo01.utils.ModuleEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

public record WorkFlowTransitionInfoDto (

     Long id,
     String fromStatus,
     String toStatus,
     String description,
     ModuleEnum module,

     String labelName,

     String actionType,

     List<ApprovalPolicyInfoDto> approvalPolicyInfoDtoList,

     Boolean enabled,

     String createdBy,
     Instant createdDate,
     String lastModifiedBy,
     Instant lastModifiedDate
){}
