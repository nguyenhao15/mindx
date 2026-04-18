package com.example.demo01.domains.jpa.Core.Approval.entities;

import com.example.demo01.utils.BaseEntity.Jpa.BaseAuditJpaModel;
import com.example.demo01.utils.ModuleEnum;
import jakarta.persistence.*;
import lombok.*;
import software.amazon.awssdk.core.pagination.sync.PaginatedResponsesIterator;

@Entity
@Table(name = "approval_policies",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"target_status","requester_position","module"})
        },
        indexes = {
        @Index(name = "idx_dept_status", columnList = "module, target_status, requester_position")
})
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ApprovalPolicyEntity extends BaseAuditJpaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "target_status", length = 50, nullable = false)
    private String targetStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ModuleEnum module;

    @Column(name = "requester_position", nullable = false, length = 50)
    private String requesterPosition;

    @Enumerated(EnumType.STRING)
    private AllowTypeEnum allowType;

    private String description;

    private String allowValue;

    private Integer priority = 0;

    @Column(name = "is_active")
    private Boolean isActive = true;
}
