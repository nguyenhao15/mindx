package com.example.demo01.domains.jpa.Core.Audit.dto;

import com.example.demo01.core.AuditUpdate.model.ChangeType;
import com.example.demo01.utils.ModuleEnum;

import java.time.Instant;

public record AuditUpdateDto (
    Long id,
    String identifier,
    String description,
    String updateValue,

    ChangeType changeType,

    ModuleEnum module,

    Instant createdDate,
    Instant lastModifiedDate,

    String createdBy,
    String lastModifiedBy
) {};