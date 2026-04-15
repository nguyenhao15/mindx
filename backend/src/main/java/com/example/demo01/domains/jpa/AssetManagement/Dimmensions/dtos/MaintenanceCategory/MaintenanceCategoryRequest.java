package com.example.demo01.domains.jpa.AssetManagement.Dimmensions.dtos.MaintenanceCategory;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MaintenanceCategoryRequest {

    @NotBlank(message = "Category title is required")
    private String categoryTitle;

    private Boolean hashChild = true;

    private String description;

    private Boolean isDeleted = false;

    private Boolean active = true;
}
