package com.example.demo01.utils.BaseEntity.Mongo;

import lombok.Data;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;

@Data
public abstract class BaseAuditModel {

    @CreatedBy // Tự động lấy User đang login khi tạo mới
    @Field("created_by")
    protected String createdBy;

    @LastModifiedBy // Tự động lấy User đang login khi update
    @Field("last_modified_by")
    protected String lastModifiedBy;

    @CreatedDate // Tự động lấy giờ hệ thống khi tạo mới
    @Field("created_date")
    protected Instant createdDate;

    @LastModifiedDate // Tự động lấy giờ hệ thống khi update
    @Field("last_modified_date")
    protected Instant lastModifiedDate;

    @Version
    protected Long version;
}
