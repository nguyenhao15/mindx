package com.example.demo01.core.Auth.models;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;


@Data
@Document(collection = "sessions")
@AllArgsConstructor
@NoArgsConstructor
public class Session {

    @Id
    private String _id;

    @Indexed(unique = true)
    private String staffId;

    @Indexed(unique = true)
    private String refreshToken;

    @Indexed(expireAfter = "0s")
    private Instant expiryDate;

    @CreatedDate
    private Instant createdDate;

    @LastModifiedDate
    private Instant updatedDate;

    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String updatedBy;
}
