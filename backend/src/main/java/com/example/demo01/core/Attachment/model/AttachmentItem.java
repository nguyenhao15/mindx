package com.example.demo01.core.Attachment.model;

import com.example.demo01.utils.BaseEntity.Mongo.BaseAuditModel;
import com.example.demo01.utils.ModuleEnum;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Document(collection = "attachment_collection")
public class AttachmentItem extends BaseAuditModel {

    @Id
    private String id;

    private String fileName;

    private String pathName;

    private String fileType;

    private String fileUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "module", length = 20)
    private ModuleEnum module;

    private Boolean isPublic;

    private Boolean isDeleted;

    private long fileSize;

    private String ownerId;

}
