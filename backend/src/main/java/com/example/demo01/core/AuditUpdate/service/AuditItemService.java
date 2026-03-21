package com.example.demo01.core.AuditUpdate.service;

import com.example.demo01.core.AuditUpdate.Dto.AuditUpdateDto;
import com.example.demo01.core.AuditUpdate.model.AuditItem;

public interface AuditItemService {

    AuditItem createAuditItem(AuditUpdateDto auditItem);

    AuditItem getAuditItemsByEntityId(String entityId);
}
