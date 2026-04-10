package com.example.demo01.core.Basement.model;

import com.example.demo01.utils.BaseEntity.Mongo.BaseAuditModel;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "buCollection")
public class BranchUnit extends BaseAuditModel {

    @Id
    private String id;

    @Indexed
    private String buFullName;

    @Indexed(unique = true)
    private String buId;

    @Indexed(unique = true)
    private String accountantCode; // Dùng camelCase cho đúng chuẩn Java

    private String buType;

    private Boolean active;

    private Double size;

    private String address;

    private String city;
    private String areaFullName;
}
