package com.example.demo01.domains.MiniCrm.Contract.service;

import com.example.demo01.domains.MiniCrm.Contract.dtos.contractUtils.CalPriceRequest;
import com.example.demo01.domains.MiniCrm.Contract.dtos.contractUtils.CalTotalContractValueRequest;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public interface ContractUtilService {

    Double calculateTotalValue(CalTotalContractValueRequest calTotalContractValueRequest);

    Double calculatePrice(CalPriceRequest calPriceRequest);

    <T> Set<String> extractIds(Collection<T> items, Function<T, String> extractor);

    <T> Map<String, String> buildReferenceMap(List<T> entities,
                                              Function<T, String> keyMapper,
                                              Function<T, String> valueMapper);
}
