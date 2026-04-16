package com.example.demo01.domains.jpa.Core.Audit.service;

import com.example.demo01.domains.jpa.Core.Audit.dto.AuditUpdateDto;
import com.example.demo01.domains.jpa.Core.Audit.dto.AuditUpdateRequest;

import java.util.List;

public interface AuditUpdateService {

    AuditUpdateDto createAuditUpdate(AuditUpdateRequest request);

    List<AuditUpdateDto> getAuditUpdatesByEntityName(String entityName);

    void deleteAuditUpdatesByEntityName(String entityName);
}
