// src/main/java/com/example/demo01/domains/Dimmesion/model/Ward.java
package com.example.demo01.domains.mongo.MiniCrm.Dimmesion.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Ward {

    @Field("AdministrativeUnitId")
    private Integer administrativeUnitId;

    @Field("Code")
    private String code;

    @Field("CodeName")
    private String codeName;

    @Field("FullName")
    private String fullName;

    @Field("FullNameEn")
    private String fullNameEn;

    @Field("Name")
    private String name;

    @Field("NameEn")
    private String nameEn;

    @Field("ProvinceCode")
    private String provinceCode;

    @Field("Type")
    private String type;
}