package com.example.demo01.domains.jpa.Core.Audit.dto;

import com.example.demo01.core.AuditUpdate.model.ChangeType;
import com.example.demo01.utils.ModuleEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@NotBlank()
public class AuditUpdateRequest {

    @NotBlank
    private String itemId;

    @NotBlank(message = "Identifier is required")
    private String identifier;

    @NotBlank(message = "Change type is required")
    private ChangeType changeType;

    @NotBlank(message = "Update value is required")
    private String updateValue;

    @NotNull(message = "Module is required")
    private ModuleEnum module;

    @NotBlank(message = "Description is required")
    private String description;
}
