package com.example.demo01.domains.jpa.AssetManagement.Maintenance.entities;


import com.example.demo01.domains.jpa.AssetManagement.Dimmensions.entities.MaintenanceCategoryEntity;
import com.example.demo01.domains.jpa.AssetManagement.Dimmensions.entities.MaintenanceItemEntity;
import com.example.demo01.domains.jpa.AssetManagement.Utils.MaintenancesStatus;
import com.example.demo01.utils.BaseEntity.Jpa.BaseAuditJpaModel;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
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

    @Column(name = "inspect_at")
    private LocalDate inspectAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maintenance_category_id")
    private MaintenanceCategoryEntity fixCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maintenance_item_id")
    private MaintenanceItemEntity fixItem;

    private LocalDate issueDate;

    private String maintenancesStatus = "WAITING";

    @OneToMany(mappedBy = "maintenance", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MaintenancesProposals> maintenancesProposals = new ArrayList<>();

    private String locationId;

    private String assignedTo;

    private Double totalCost = 0.0;

    private Boolean reWork;

    private Boolean isDeleted = false;

    public boolean isReadyToComplete() {
        return this.completionAt != null && this.verifiedAt != null;

    }

}
