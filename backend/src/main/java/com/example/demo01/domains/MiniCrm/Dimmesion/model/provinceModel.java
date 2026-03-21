package com.example.demo01.domains.MiniCrm.Dimmesion.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "provincesData")
public class provinceModel {

    @Id
    @Field("_id")
    private String id;

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

    @Field("Type")
    private String type;

    @Field("Wards")
    private List<Ward> wards;
}
