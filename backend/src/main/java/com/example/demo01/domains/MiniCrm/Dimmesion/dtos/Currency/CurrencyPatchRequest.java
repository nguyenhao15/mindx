package com.example.demo01.domains.MiniCrm.Dimmesion.dtos.Currency;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CurrencyPatchRequest {

    private String currencyName;
    private String currencyCode;

    @Builder.Default
    private Boolean active = false;
}
