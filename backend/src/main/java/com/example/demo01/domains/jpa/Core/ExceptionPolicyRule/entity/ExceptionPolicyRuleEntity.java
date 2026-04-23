package com.example.demo01.domains.jpa.Core.ExceptionPolicyRule.entity;

import com.example.demo01.utils.BaseEntity.Jpa.BaseAuditJpaModel;
import com.example.demo01.utils.ModuleEnum;
import com.example.demo01.utils.ScopeView;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity(name = "policy_rules")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ExceptionPolicyRuleEntity extends BaseAuditJpaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String staffId;

    private ModuleEnum moduleEnum;

    @Enumerated(EnumType.STRING)
    private ScopeView scopeView;

    private LocalDate activationDate;

    private LocalDate expirationDate;

    private Boolean active;
}
