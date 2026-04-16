package com.example.demo01.domains.jpa.Core.Audit.service;

import com.example.demo01.domains.jpa.Core.Audit.dto.AuditUpdateDto;

public interface AuditUpdateService {

    AuditUpdateDto createAuditUpdate(String entityName, String operationType, String userId);
}
