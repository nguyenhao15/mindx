package com.example.demo01.domains.MiniCrm.Dimmesion.service;

import com.example.demo01.domains.MiniCrm.Dimmesion.dtos.Currency.CurrencyInfoDto;
import com.example.demo01.domains.MiniCrm.Dimmesion.dtos.Currency.CurrencyPatchRequest;

import java.util.List;

public interface CurrencyService {

    List<CurrencyInfoDto> getAllCurrency();

    List<CurrencyInfoDto> getActiveCurrency(Boolean active);

    CurrencyInfoDto createNewCurrency(CurrencyPatchRequest currencyPatchRequest);

    CurrencyInfoDto updateCurrency(String id,CurrencyPatchRequest currencyPatchRequest);

    CurrencyInfoDto getCurrency(String id);

    String deleteCurrency(String id);

}
