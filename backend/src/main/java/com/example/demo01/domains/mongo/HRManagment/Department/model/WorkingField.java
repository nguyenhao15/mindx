package com.example.demo01.domains.mongo.HRManagment.Department.model;


import com.example.demo01.utils.BaseEntity.Mongo.BaseAuditModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
@Document(collection = "workingFields")
public class WorkingField extends BaseAuditModel {

    private String id;

    @Indexed
    private String fieldName;

    @Indexed(unique = true)
    private String fieldCode;

    private String description;

    private Boolean active;

}
