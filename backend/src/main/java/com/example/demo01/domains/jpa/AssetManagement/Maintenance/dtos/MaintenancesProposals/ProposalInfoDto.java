package com.example.demo01.domains.jpa.AssetManagement.Maintenance.dtos.MaintenancesProposals;

import com.example.demo01.domains.jpa.AssetManagement.Utils.ProposalStatusEnum;

import java.time.Instant;

public record ProposalInfoDto (

    Long id,

    Long maintenanceId,

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
