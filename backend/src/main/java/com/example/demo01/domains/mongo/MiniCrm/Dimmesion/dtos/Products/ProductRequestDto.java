package com.example.demo01.domains.mongo.MiniCrm.Dimmesion.dtos.Products;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductRequestDto {

    @NotBlank(message = "Service short name must not be blank")
    private String serviceShortName;

    @NotBlank(message = "Service Name must not be blank")
    private String serviceName;

    private String serviceTag;
    private Boolean isParent;

    private Double calculationValue;
    private String serviceUnit;
}
