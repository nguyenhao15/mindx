package com.example.demo01.core.Basement.service;

import com.example.demo01.core.Basement.dto.basement.BUInfoDto;
import com.example.demo01.core.Basement.dto.basement.BUPatchRequestDto;
import com.example.demo01.core.Basement.dto.basement.BURequestDto;
import com.example.demo01.core.Basement.model.BranchUnit;
import com.example.demo01.utils.BasePageResponse;
import com.example.demo01.utils.FilterWithPagination;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public interface BasementService {

    BUInfoDto createNewBu(BURequestDto requestDto);

    void createBus(List<BURequestDto> requestDtos);

    BUInfoDto getBuInfoById(String id);

    BUInfoDto getBuInfoByShortName(String shortName);

    List<BUInfoDto> getActiveBuInfos();

    BasePageResponse<BUInfoDto> getAllBU(FilterWithPagination filter);

    BasePageResponse<BUInfoDto> buildPageResponse(Page<BranchUnit> page);

    BUInfoDto updateBUById(String id, BUPatchRequestDto patchRequestDto);

    String deleteBUById(String id);

    <T> Map<T, BUInfoDto> getBatchBuIds(List<T> inputs, Function<T, String> idExtractor);

    Map<String, Object> getBatchBuFullNames(List<String> buShortNames);
}
