package com.example.demo01.domains.jpa.AssetManagement.Maintenance.dtos.MaintenancesProposals;

import com.example.demo01.domains.jpa.AssetManagement.Utils.ProposalStatusEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MaintenancesProposalRequest {

    @NotBlank(message = "Maintenance ID is required")
    private Long maintenanceId;

    @NotBlank(message = "Proposal description is required")
        private String proposalDescription;

    @NotNull(message = "Proposal cost is required")
    private Double proposalCost;

    private String proposedBy;

    private ProposalStatusEnum proposalStatus = ProposalStatusEnum.PROPOSAL_PENDING;

}
