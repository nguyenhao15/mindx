package com.example.demo01.domains.jpa.Core.ExceptionPolicyRule.dtos;

import com.example.demo01.utils.ModuleEnum;
import com.example.demo01.utils.ScopeView;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExceptionPolicyRuleRequestDto {


    @NotNull
    private String staffId;

    @NotNull
    private ModuleEnum moduleEnum;

    private ScopeView scopeView;

    private LocalDate activationDate;

    private LocalDate expirationDate;

    private Boolean active = true;

}
