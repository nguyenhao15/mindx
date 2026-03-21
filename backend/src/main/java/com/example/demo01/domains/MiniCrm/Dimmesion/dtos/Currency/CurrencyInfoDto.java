package com.example.demo01.domains.MiniCrm.Dimmesion.dtos.Currency;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyInfoDto {
    private String _id;
    private String currencyName;
    private String currencyCode;
    private Boolean active;
}
