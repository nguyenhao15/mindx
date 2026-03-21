package com.example.demo01.domains.MiniCrm.Payment.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TransactionAllocate {

    private String identifier;

    private String allocateItem;
    private Double allocateAmount;

    @CreatedDate
    private Instant createdAt;

    @Field("created_by")
    protected String createdBy;
}
