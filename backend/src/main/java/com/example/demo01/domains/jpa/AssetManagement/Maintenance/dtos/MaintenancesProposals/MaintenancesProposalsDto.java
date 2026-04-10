package com.example.demo01.domains.jpa.AssetManagement.Maintenance.dtos.MaintenancesProposals;

import com.example.demo01.domains.jpa.AssetManagement.Maintenance.dtos.Maintenance.MaintenanceSummaryDTO;
import com.example.demo01.domains.jpa.AssetManagement.Utils.ProposalStatusEnum;

import java.time.Instant;

public record MaintenancesProposalsDto(

    Long id,

    MaintenanceSummaryDTO maintenanceItem,

    String proposalDescription,

    Double proposalCost,

    String proposedBy,

    ProposalStatusEnum proposalStatus,

    String createdBy,

    String lastModifiedBy,

    Instant lastModifiedDate,

    Instant createdDate,

    Long version
) {}
