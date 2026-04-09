package com.example.demo01.domains.jpa.AssetManagement.Dimmensions.entities;

import com.example.demo01.utils.BaseModels.BaseAuditJpaModel;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "maintenanceCategory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MaintenanceItemEntity> maintenanceItems = new ArrayList<>();

}
