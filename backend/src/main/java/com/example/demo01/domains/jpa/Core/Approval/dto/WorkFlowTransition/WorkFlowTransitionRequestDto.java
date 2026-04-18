package com.example.demo01.domains.jpa.Core.Approval.dto.WorkFlowTransition;

import com.example.demo01.utils.ModuleEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WorkFlowTransitionRequestDto {

    @NotBlank(message = "From status is required")
    private String fromStatus;

    @NotBlank(message = "To status is required")
    private String toStatus;

    @NotNull(message = "Module is required")
    private ModuleEnum module;

    private String description;

    private Boolean enabled;

}
