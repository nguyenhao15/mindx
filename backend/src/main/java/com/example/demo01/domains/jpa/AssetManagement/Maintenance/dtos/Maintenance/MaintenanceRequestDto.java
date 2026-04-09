package com.example.demo01.domains.jpa.AssetManagement.Maintenance.dtos.Maintenance;

import com.example.demo01.domains.jpa.AssetManagement.Utils.MaintenancesStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MaintenanceRequestDto {

    @NotBlank(message = "Mô tả không được để trống")
    private String description;

    @NotNull(message = "Vui lòng chọn danh mục bảo trì")
    private Long maintenanceItemId;

    @NotNull(message = "Vui lòng chọn hạng mục cần sửa")
    private Long maintenanceCategoryId;

    private LocalDate issueDate;

    private MaintenancesStatus maintenancesStatus = MaintenancesStatus.PENDING;

    private String locationId;

    private Double totalCost;
}
