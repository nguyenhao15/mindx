package com.example.demo01.domains.jpa.Core.Approval.entities;

import com.example.demo01.utils.BaseEntity.Jpa.BaseAuditJpaModel;
import com.example.demo01.utils.ModuleEnum;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "workflow_transitions")
@Table(name = "workflow_transitions",
        indexes = {
                @Index(name = "idx", columnList = "module, from_status, to_status")
        },
        uniqueConstraints = {
        @UniqueConstraint(columnNames = {"from_status","to_status","module"})
})
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class WorkFlowTransitionEntity extends BaseAuditJpaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ModuleEnum module;

    @Column(name = "from_status", length = 50, nullable = false)
    private String fromStatus;

    @Column(name = "to_status", length = 50, nullable = false)
    private String toStatus;

    private String labelName;

    private String operator = "EQ";

    private String actionType;

    private String description;

    private Boolean enabled = true;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "workflowAction", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ApprovalPolicyEntity> approvalPolicyEntity = new ArrayList<>();
}
