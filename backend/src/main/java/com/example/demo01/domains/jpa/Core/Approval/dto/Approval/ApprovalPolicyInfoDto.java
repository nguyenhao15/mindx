package com.example.demo01.domains.jpa.Core.Approval.dto.Approval;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApprovalPolicyInfoDto {

    private Long id;
    private String currentStatus;
    private List<String> approverPositions;
    private String requesterPosition;
    private String module;
    private Integer priority;
    private Boolean isActive;

}
