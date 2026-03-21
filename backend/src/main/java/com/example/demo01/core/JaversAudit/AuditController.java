// java
package com.example.demo01.core.JaversAudit;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/audit")
public class AuditController {

    private final AuditService auditService;

    public AuditController(AuditService auditService) {
        this.auditService = auditService;
    }

    @GetMapping("/{entityClass}/{entityId}")
    public ResponseEntity<List<AuditHistoryDto>> getHistory(
            @PathVariable String entityClass,
            @PathVariable String entityId) {
        Class<?> clazz;
        try {
            clazz = Class.forName(entityClass);
        } catch (ClassNotFoundException e) {
            return ResponseEntity.badRequest().build();
        }

        List<AuditHistoryDto> history = auditService.getHistory(clazz, entityId);
        return ResponseEntity.ok(history);
    }
}