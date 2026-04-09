package com.example.demo01.domains.jpa.AssetManagement.Maintenance.entities;


import com.example.demo01.domains.jpa.AssetManagement.Dimmensions.entities.MaintenanceCategoryEntity;
import com.example.demo01.domains.jpa.AssetManagement.Dimmensions.entities.MaintenanceItemEntity;
import com.example.demo01.domains.jpa.AssetManagement.Utils.MaintenancesStatus;
import com.example.demo01.utils.BaseModels.BaseAuditJpaModel;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity(name = "maintenances")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MaintenanceEntity extends BaseAuditJpaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @Column(name = "completion_at")
    private LocalDate completionAt;

    @Column(name = "verified_at")
    private LocalDate verifiedAt;

    private LocalDate inspection_at;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maintenance_category_id")
    private MaintenanceCategoryEntity fixCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maintenance_item_id")
    private MaintenanceItemEntity fixItem;

    private LocalDate issueDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "maintenances_status", length = 20)
    private MaintenancesStatus maintenancesStatus = MaintenancesStatus.PENDING;

    @OneToMany(mappedBy = "maintenance", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MaintenancesProposals> maintenancesProposals;

    private String locationId;

    private Double totalCost;

    public boolean isReadyToComplete() {
        return this.completionAt != null && this.verifiedAt != null;

    }

}
