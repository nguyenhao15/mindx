package com.example.demo01.domains.jpa.AssetManagement.Maintenance.dtos.Maintenance;

import com.example.demo01.domains.jpa.Core.Audit.dto.AuditUpdateRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MaintenanceUpdateRequest {
    @NotNull(message = "Vui lòng cung cấp ID bảo trì")
    private MaintenanceRequestDto requestDto;

    @NotNull(message = "Thông tin cập nhật là bắt buộc")
    private AuditUpdateRequest auditUpdateRequest;

}
