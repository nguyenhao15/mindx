package com.example.demo01.core.Basement.dto.basement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PriceList {

    private String priceName;
    private String buShortName;
    private Double priceValue;
}
