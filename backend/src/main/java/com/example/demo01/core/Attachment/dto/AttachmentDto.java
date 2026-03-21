package com.example.demo01.core.Attachment.dto;

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
    private long fileSize;
    private String ownerId;

    private String createdBy;
    private String lastModifiedBy;

    private Instant createdDate;
    private Instant lastModifiedDate;

    private Long version;

}
