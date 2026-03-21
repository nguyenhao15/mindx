package com.example.demo01.core.AuditUpdate.model;

import com.example.demo01.core.AuditUpdate.Dto.AuditUpdateDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Document(collection = "audit_updates")
@AllArgsConstructor
@Data
@NoArgsConstructor
public class AuditItem {

    private String _id;
    private List<AuditUpdateDto> updates;
    private String entityId;

    @CreatedBy
    private String author;

    @CreatedDate
    private Instant createdDate;

    @LastModifiedBy
    private String modifiedBy;

    @LastModifiedDate
    private Instant modifiedAt;
}
