// java
package com.example.demo01.core.JaversAudit;

import java.util.List;

public interface AuditService {
    List<AuditHistoryDto> getHistory(Class<?> entityClass, String entityId);
}
