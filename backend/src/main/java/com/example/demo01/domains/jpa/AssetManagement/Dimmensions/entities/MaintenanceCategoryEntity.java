package com.example.demo01.domains.jpa.AssetManagement.Dimmensions.entities;

import com.example.demo01.utils.BaseModels.BaseAuditJpaModel;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

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

}
