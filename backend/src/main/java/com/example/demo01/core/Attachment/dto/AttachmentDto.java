package com.example.demo01.core.Attachment.dto;

import com.example.demo01.utils.ModuleEnum;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AttachmentDto {

    private String id;
    private String fileName;
    private String pathName;
    private String fileType;
    private String fileUrl;
    private Boolean isPublic;
    private Boolean isDeleted;

    @Enumerated(EnumType.STRING)
    @Column(name = "module", length = 20)
    private ModuleEnum module;

    private long fileSize;
    private String ownerId;

    private String createdBy;
    private String lastModifiedBy;

    private Instant createdDate;
    private Instant lastModifiedDate;

    private Long version;

}
