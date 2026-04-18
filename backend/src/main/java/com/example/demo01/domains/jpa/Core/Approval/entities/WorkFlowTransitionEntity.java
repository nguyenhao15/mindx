package com.example.demo01.domains.jpa.Core.Approval.entities;

import com.example.demo01.utils.BaseEntity.Jpa.BaseAuditJpaModel;
import com.example.demo01.utils.ModuleEnum;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "workflow_transitions")
@Table(name = "workflow_transitions",
        indexes = {
                @Index(name = "idx", columnList = "module, from_status, to_status, enabled")
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

    private ModuleEnum module;

    @Column(name = "from_status", length = 50, nullable = false)
    private String fromStatus;

    @Column(name = "to_status", length = 50, nullable = false)
    private String toStatus;

    private String description;

    private Boolean enabled = true;

}
