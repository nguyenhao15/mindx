package com.example.demo01.domains.jpa.Core.Approval.dto.Approval;

import com.example.demo01.domains.jpa.Core.Approval.entities.AllowTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public record ApprovalPolicyInfoDto (

     Long id,

     String targetStatus,

     AllowTypeEnum allowType,

     String allowValue,

     Long workFlowId,

     String description,

     String requesterPosition,

     String module,

     Integer priority,

     Boolean isActive

){}