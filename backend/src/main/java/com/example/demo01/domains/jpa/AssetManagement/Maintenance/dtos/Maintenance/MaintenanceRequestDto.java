package com.example.demo01.domains.jpa.AssetManagement.Maintenance.dtos.Maintenance;

import com.example.demo01.domains.jpa.AssetManagement.Utils.MaintenancesStatus;
import jakarta.persistence.Column;
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

    private LocalDate completionAt;

    private String maintenanceType = "INTERNAL";

    private LocalDate verifiedAt;

    private LocalDate inspectAt;

    private LocalDate issueDate;

    private String assignedTo;

    private String maintenancesStatus = "WAITING";

    private String locationId;

    private Double totalCost = 0.0;

    private Boolean reWork = false;

    private Boolean isDeleted = false;

}
