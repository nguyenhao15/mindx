package com.example.demo01.core.Basement.model;


import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "roomDatabase")
public class RoomModel {

    @Id
    private String _id;

    private String roomShortName;
    private String buShortName;

    private String roomName;
    private String roomTag;

    private Double roomSize;

    private Boolean isActive;

    private Boolean commercial;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;

}
