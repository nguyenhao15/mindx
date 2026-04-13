package com.example.demo01.domains.jpa.AssetManagement.Dimmensions.entities;

import com.example.demo01.utils.BaseEntity.Jpa.BaseAuditJpaModel;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "fixCategory")
@Getter
@Setter
public class MaintenanceCategoryEntity extends BaseAuditJpaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String categoryTitle;

    private String description;

    private Boolean hashChild = true;

    private Boolean isDeleted = false;

    private Boolean active = true;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "maintenanceCategory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MaintenanceItemEntity> maintenanceItems = new ArrayList<>();

}
