package com.example.demo01.core.AuditUpdate.controller;

import com.example.demo01.core.AuditUpdate.service.AuditItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/audit-update")
@RequiredArgsConstructor
@RestController
public class AuditUpdateController {

    private final AuditItemService auditItemService;

    @GetMapping("/entity/{entityId}")
    public ResponseEntity<?> getAuditItemsByEntityId(@PathVariable String entityId) {
        return ResponseEntity.ok(auditItemService.getAuditItemsByEntityId(entityId));
    }

}
