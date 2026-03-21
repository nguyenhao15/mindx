package com.example.demo01.domains.MiniCrm.Dimmesion.dtos.Products;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductPatchRequestDto {
    private String serviceShortName;
    private String serviceName;

    private String serviceTag;
    private Boolean isParent;

    private Double calculationValue;
    private String serviceUnit;
}
