package com.example.demo01.domains.jpa.AssetManagement.Maintenance.entities;

import com.example.demo01.domains.jpa.AssetManagement.Utils.ProposalStatusEnum;
import com.example.demo01.utils.BaseEntity.Jpa.BaseAuditJpaModel;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "maintenances_proposals")
@Getter
@Setter
public class MaintenancesProposals extends BaseAuditJpaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maintenance_id")
    private MaintenanceEntity maintenance;

    private String proposalDescription;

    private Double proposalCost;

    private String proposedBy;

    @Enumerated(EnumType.STRING)
    private ProposalStatusEnum proposalStatus = ProposalStatusEnum.PROPOSAL_PENDING;

    private Boolean isDeleted = false;

}
