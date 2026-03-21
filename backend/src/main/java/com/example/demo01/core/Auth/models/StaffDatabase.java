package com.example.demo01.core.Auth.models;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "staffs")
public class StaffDatabase {

    @Id
    private String _id;
    private String userId;

    private String staffId;
    private String staffName;

    private String systemRole;
    private String appName;

    private String departmentId;
    private String positionCode;

    private String positionLevel;
}
