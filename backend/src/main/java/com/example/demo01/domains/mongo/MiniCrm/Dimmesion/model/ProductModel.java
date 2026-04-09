package com.example.demo01.domains.mongo.MiniCrm.Dimmesion.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "products")
public class ProductModel {

    @Id
    @Indexed
    private String _id;

    @Indexed(unique = true)
    private String serviceShortName;

    private String serviceName;

    private String serviceTag;

    private Boolean isParent;

    private Double calculationValue;
    private String serviceUnit;
}
