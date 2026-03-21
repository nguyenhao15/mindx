package com.example.demo01.core.Auth.models;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(collection = "roles")
public class Role {

    @Id
    private String _id;

    private AppRole roleName;
    private String appName;

    public Role(AppRole roleName) {
        this.roleName = roleName;
    }
}