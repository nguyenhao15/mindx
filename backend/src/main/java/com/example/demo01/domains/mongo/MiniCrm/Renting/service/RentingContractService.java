package com.example.demo01.domains.mongo.MiniCrm.Renting.service;

import com.example.demo01.core.Basement.dto.room.RoomInfoDto;
import com.example.demo01.domains.mongo.MiniCrm.Contract.dtos.appendix.AppendixRequest;
import com.example.demo01.domains.mongo.MiniCrm.Renting.dtos.*;
import com.example.demo01.domains.mongo.MiniCrm.Renting.model.RentingStatus;
import com.example.demo01.utils.PageInput;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public interface RentingContractService {
    RentingDto createRentingRequest(RentingRequest request);

    RentingDto previewRentingRequest(AppendixRequest appendixRequest, RentingRequest request);

    RentingDto handleRentingItemFromAction(AppendixRequest appendixRequest , RentingAction request);

    RentingDto getRentingById(String id);

    RentingResponse getAllRenting(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    List<RentingDto> getRentingByContractId(String contractId);

    List<RoomInfoDto> getAvailableRoomForRenting(String buShortName, List<String> roomIds);

    RentingResponse getRentingByBuShortName(String buShortName, PageInput pageInput);

    List<RentingDto> getRentingByCustomerId(String customerId);

    RentingResponse getRentingByDefault(String status, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    List<RentingDto> getActiveRentingByBuShortName(String buShortName);

    RentingDto updateRentingById(String id, RentingPatchRequest request);

    String deleteRentingById(String id);

    void deleteByContractId(String contractId);

    List<RentingDto> getBatchRentingByContractCode(String contractCode);

    <T> Map<T, List<RentingDto>> getBatchRenting(List<T> inputs, Function<T, String> contractCodeExtractor);

    List<RentingDto> updateRentingActiveStatus(List<String> ids, Boolean active);

    List<RentingDto> updateUnActiveRentingByContractId(String contractId, RentingStatus updateType, LocalDate updateDate);
}
