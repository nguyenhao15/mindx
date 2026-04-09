package com.example.demo01.domains.jpa.AssetManagement.Dimmensions.dtos.MaintenanceItem;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MaintenanceItemRequest {
    @NotBlank(message = "Item title must not be blank")
    private String itemTitle;

    private String description;

    @NotNull(message = "Category ID must not be blank")
    private Long categoryId;
}
