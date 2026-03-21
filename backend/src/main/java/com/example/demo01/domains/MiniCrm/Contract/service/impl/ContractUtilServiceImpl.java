package com.example.demo01.domains.MiniCrm.Contract.service.impl;

import com.example.demo01.domains.MiniCrm.Contract.dtos.contractUtils.CalPriceRequest;
import com.example.demo01.domains.MiniCrm.Contract.dtos.contractUtils.CalTotalContractValueRequest;
import com.example.demo01.domains.MiniCrm.Contract.service.ContractUtilService;
import com.example.demo01.domains.MiniCrm.Utils.MC_Utils;
import com.example.demo01.utils.AppUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContractUtilServiceImpl implements ContractUtilService {

    private final MC_Utils mcUtils;

    @Override
    public Double calculateTotalValue(CalTotalContractValueRequest calTotalContractValueRequest) {
        return mcUtils.calTotalValue(calTotalContractValueRequest.getStartDate(), calTotalContractValueRequest.getEndDate(), calTotalContractValueRequest.getPricePerUnit(), calTotalContractValueRequest.getUnit());
    }

    @Override
    public Double calculatePrice(CalPriceRequest calPriceRequest) {
        return mcUtils.calPrice(calPriceRequest);
    }

    @Override
    public <T> Set<String> extractIds(Collection<T> items, Function<T, String> extractor) {
        if (CollectionUtils.isEmpty(items)) return Collections.emptySet();
        return items.stream()
                .map(extractor)
                .filter(StringUtils::hasText) // Check null & empty
                .collect(Collectors.toSet());
    }

    @Override
    public <T> Map<String, String> buildReferenceMap(List<T> entities, Function<T, String> keyMapper, Function<T, String> valueMapper) {
        if (CollectionUtils.isEmpty(entities)) return Collections.emptyMap();
        return entities.stream()
                .collect(Collectors.toMap(
                        keyMapper,
                        valueMapper,
                        (existing, replacement) -> existing // Xử lý trùng key: giữ giá trị cũ, tránh crash
                ));
    }

}
