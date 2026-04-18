package com.example.demo01.domains.jpa.Core.Approval.dto.WorkFlowTransition;

import com.example.demo01.utils.ModuleEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WorkFlowTransitionInfoDto {

    private Long id;

    private String fromStatus;
    private String toStatus;
    private String description;
    private ModuleEnum module;

    private String createdBy;
    private Instant createdDate;
    private String lastModifiedBy;
    private Instant lastModifiedDate;
}
