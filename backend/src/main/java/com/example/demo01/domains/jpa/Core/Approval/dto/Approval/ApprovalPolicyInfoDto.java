package com.example.demo01.domains.jpa.Core.Approval.dto.Approval;

import com.example.demo01.domains.jpa.Core.Approval.entities.AllowTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApprovalPolicyInfoDto {

    private Long id;
    private String currentStatus;

    private AllowTypeEnum allowType;

    private String allowValue;

    private String requesterPosition;
    private String module;
    private Integer priority;
    private Boolean isActive;

}
