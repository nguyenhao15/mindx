package com.example.demo01.core.Auth.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "departmentDatabase")
public class Department {

    private String _id;
    private String departmentName;
    private String managerId;

}
