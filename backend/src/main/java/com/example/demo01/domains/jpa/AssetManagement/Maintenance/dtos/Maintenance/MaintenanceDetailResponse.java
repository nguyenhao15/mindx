package com.example.demo01.domains.jpa.AssetManagement.Maintenance.dtos.Maintenance;

import com.example.demo01.core.Attachment.dto.AttachmentDto;
import com.example.demo01.domains.jpa.Core.Audit.dto.AuditUpdateDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MaintenanceDetailResponse{
        MaintenanceDetailsInfoDto maintenanceDetailsInfo;
        List<AuditUpdateDto> updateHistory;
        List<AttachmentDto> files;
}
