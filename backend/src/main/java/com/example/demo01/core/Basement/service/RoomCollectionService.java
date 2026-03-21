package com.example.demo01.core.Basement.service;

import com.example.demo01.core.Basement.dto.room.RoomDtoResponse;
import com.example.demo01.core.Basement.dto.room.RoomInfoDto;
import com.example.demo01.core.Basement.dto.room.RoomPatchRequest;
import com.example.demo01.core.Basement.dto.room.RoomRequestDto;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public interface RoomCollectionService {

    RoomInfoDto createRoom(RoomRequestDto requestDto);

    RoomInfoDto getRoomById(String id);

    List<RoomInfoDto> getRoomByBuShorNameWithoutPagination(String buShortName);

    List<RoomInfoDto> getRoomByBuShortNameIn(List<String> buShortNames);

    RoomDtoResponse getAllRoom(Integer pageNumber, Integer pagSize, String sortBy, String sortOrder);

    RoomDtoResponse getRoomByBuShortName(String buShortName, Integer pageNumber, Integer pagSize, String sortBy, String sortOrder);

    RoomInfoDto updateRoomById(String id, RoomPatchRequest patchRequest);

    String deleteRoomById(String id);

    <T> Map<T, RoomInfoDto> getBatchRoomIds(List<T> inputs, Function<T, String> idExtractor);

    Map<String, Object> getBatchRoomName(List<String> roomIds);

}
