package com.example.demo01.core.AuditUpdate.Dto;

import com.example.demo01.core.AuditUpdate.model.ChangeType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuditUpdateDto {

    private String description;
    private ChangeType changeType;

    private String entityId;

    private String author;
    private Instant createdDate;

}
