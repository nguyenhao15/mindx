package com.example.demo01.domains.jpa.Core.ExceptionPolicyRule.dtos;

import com.example.demo01.utils.ModuleEnum;
import com.example.demo01.utils.ScopeView;

import java.time.Instant;
import java.time.LocalDate;

public record ExceptionPolicyRuleInfoDto(

        Long id,
        String staffId,
        ModuleEnum moduleEnum,
        ScopeView scopeView,
        LocalDate activationDate,
        LocalDate expirationDate,
        Boolean active,

        Instant createdDate,
        Instant lastModifiedDate,

        String createdBy,
        String lastModifiedBy

) {}