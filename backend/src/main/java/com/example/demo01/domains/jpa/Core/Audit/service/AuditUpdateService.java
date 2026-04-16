package com.example.demo01.domains.jpa.Core.Audit.service;

import com.example.demo01.domains.jpa.Core.Audit.dto.AuditUpdateDto;
import com.example.demo01.domains.jpa.Core.Audit.dto.AuditUpdateRequest;
import com.example.demo01.utils.ModuleEnum;

import java.util.List;

public interface AuditUpdateService {

    AuditUpdateDto createAuditUpdate(AuditUpdateRequest request);

    List<AuditUpdateDto> getAuditUpdatesByEntityName(ModuleEnum moduleEnum, Long id);

    void deleteAuditUpdatesByEntityName(String entityName);
}
