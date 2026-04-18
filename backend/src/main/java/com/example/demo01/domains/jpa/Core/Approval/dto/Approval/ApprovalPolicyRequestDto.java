package com.example.demo01.domains.jpa.Core.Approval.dto.Approval;

import com.example.demo01.domains.jpa.Core.Approval.entities.AllowTypeEnum;
import com.example.demo01.utils.ModuleEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApprovalPolicyRequestDto {

    @NotBlank(message = "Current status is required")
    private String currentStatus;

    @NotNull(message = "Module is required")
    private ModuleEnum module;

    @NotBlank(message = "Requester position is required")
    private String requesterPosition;

    private String description;

    @NotNull
    private AllowTypeEnum allowType;

    @NotNull
    private String allowValue;

    private Integer priority = 0;

    private Boolean isActive = true;
}
