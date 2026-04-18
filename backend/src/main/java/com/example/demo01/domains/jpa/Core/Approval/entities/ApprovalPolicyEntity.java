package com.example.demo01.domains.jpa.Core.Approval.entities;

import com.example.demo01.utils.BaseEntity.Jpa.BaseAuditJpaModel;
import com.example.demo01.utils.ModuleEnum;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;

@Entity(name = "approval_policies")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ApprovalPolicyEntity extends BaseAuditJpaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "current_status", length = 50, nullable = false)
    private String currentStatus;

    @Column(nullable = false)
    private ModuleEnum module;

    @Column(name = "requester_position", nullable = false, length = 50)
    private String requesterPosition;

    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(name = "approver_positions", columnDefinition = "varchar(50)[]", nullable = false)
    private List<String> approverPositions;

    private Integer priority = 0;

    @Column(name = "is_active")
    private Boolean isActive = true;
}
