package com.example.demo01.core.Basement.service.Implements;

import com.example.demo01.configs.Constants.CacheConstants;
import com.example.demo01.core.Basement.dto.room.RoomDtoResponse;
import com.example.demo01.core.Basement.dto.room.RoomInfoDto;
import com.example.demo01.core.Basement.dto.room.RoomPatchRequest;
import com.example.demo01.core.Basement.dto.room.RoomRequestDto;
import com.example.demo01.core.Basement.mapper.RoomMapper;
import com.example.demo01.core.Basement.model.RoomModel;
import com.example.demo01.repository.mongo.CoreRepo.BasementRepository.RoomModelRepository;
import com.example.demo01.core.Basement.service.RoomCollectionService;
import com.example.demo01.core.Exceptions.APIException;
import com.example.demo01.core.Exceptions.ResourceNotFoundException;
import com.example.demo01.utils.AppUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomCollectionImplement implements RoomCollectionService {

    private final RoomModelRepository roomModelRepository;

    private final RoomMapper roomMapper;

    private final CacheManager cacheManager;

    private final AppUtil appUtil;

    @Override
    public RoomInfoDto createRoom(RoomRequestDto requestDto) {
        RoomModel roomModel = roomModelRepository.save(roomMapper.toEntity(requestDto));

        return roomMapper.toDto(roomModel);
    }

    @Override
    public RoomInfoDto getRoomById(String id) {
        RoomModel roomModel = roomModelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("RoomModel", "_id", id));

        return roomMapper.toDto(roomModel);
    }

    @Override
    public List<RoomInfoDto> getRoomByBuShorNameWithoutPagination(String buShortName) {
        List<RoomModel> roomModelList = roomModelRepository.getByBuShortName(buShortName);
        return roomMapper.toDtoList(roomModelList);
    }

    @Override
    public List<RoomInfoDto> getRoomByBuShortNameIn(List<String> buShortNames) {
        if (buShortNames == null || buShortNames.isEmpty()) {
            throw new APIException("The list of BU short names cannot be null or empty.");
        }
        List<RoomModel> rooms = roomModelRepository.findByBuShortNameIn(buShortNames);
        return roomMapper.toDtoList(rooms);
    }

    @Override
    public RoomDtoResponse getAllRoom(Integer pageNumber, Integer pagSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageDetails = PageRequest.of(pageNumber, pagSize, sortByAndOrder);
        Page<RoomModel> page = roomModelRepository.findAll(pageDetails);

        List<RoomModel> roomModelList = page.getContent();
        List<RoomInfoDto> roomInfoDtos = roomMapper.toDtoList(roomModelList);

        RoomDtoResponse roomDtoResponse = new RoomDtoResponse();
        roomDtoResponse.setContent(roomInfoDtos);
        roomDtoResponse.setPageNumber(page.getNumber());
        roomDtoResponse.setPageSize(page.getSize());
        roomDtoResponse.setTotalElements(page.getTotalElements());
        roomDtoResponse.setTotalPages(page.getTotalPages());
        roomDtoResponse.setLastPage(page.isLast());

        return roomDtoResponse;
    }

    @Override
    public RoomDtoResponse getRoomByBuShortName(String buShortName, Integer pageNumber, Integer pagSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageDetails = PageRequest.of(pageNumber, pagSize, sortByAndOrder);
        Page<RoomModel> page = roomModelRepository.getByBuShortName(buShortName, pageDetails);

        List<RoomModel> roomModelList = page.getContent();
        List<RoomInfoDto> roomInfoDtos = roomMapper.toDtoList(roomModelList);

        RoomDtoResponse roomDtoResponse = new RoomDtoResponse();
        roomDtoResponse.setContent(roomInfoDtos);
        roomDtoResponse.setPageNumber(page.getNumber());
        roomDtoResponse.setPageSize(page.getSize());
        roomDtoResponse.setTotalElements(page.getTotalElements());
        roomDtoResponse.setTotalPages(page.getTotalPages());
        roomDtoResponse.setLastPage(page.isLast());

        return roomDtoResponse;
    }

    @Override
    public RoomInfoDto updateRoomById(String id, RoomPatchRequest patchRequest) {
        RoomModel roomModel = roomModelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("RoomModel", "_id", id));

        roomMapper.updateRoomFromDto(patchRequest, roomModel);
        RoomModel updatedItem = roomModelRepository.save(roomModel);

        return roomMapper.toDto(updatedItem);
    }

    @Override
    public String deleteRoomById(String id) {
        roomModelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("RoomModel", "_id", id));

        roomModelRepository.deleteById(id);

        return "Delete item successfully!!";
    }

    @Override
    public <T> Map<T, RoomInfoDto> getBatchRoomIds(List<T> inputs, Function<T, String> idExtractor) {
        if (inputs == null || inputs.isEmpty()) {
            return Map.of();
        }
        Cache cache = cacheManager.getCache(CacheConstants.ROOMS_CACHE);

        List<String> allIds = inputs.stream()
                .map(idExtractor)
                .distinct()
                .toList();
        Map<String, RoomInfoDto> infoDtoMap = new HashMap<>();
        List<String> missingIds = new ArrayList<>();


        for (String id : allIds) {
            assert cache != null;
            RoomInfoDto cached = cache.get(id, RoomInfoDto.class);
            if (cached != null) {
                infoDtoMap.put(id, cached);
            } else  {
                missingIds.add(id);
            }
        }

        if (!missingIds.isEmpty()) {
            List<RoomModel> entities = roomModelRepository.findAllById(missingIds);
            Map<String, RoomInfoDto> freshData = entities.stream()
                    .collect(Collectors.toMap(RoomModel::get_id, roomMapper::toDto));
            freshData.forEach((id, dto) -> {
                cache.put(id, dto);
                infoDtoMap.put(id, dto);
            });
        }

        return inputs.stream().collect(Collectors.toMap(
                input -> input,
                input -> infoDtoMap.get(idExtractor.apply(input))
        ));
    }

    @Override
    public Map<String, Object> getBatchRoomName(List<String> roomIds) {
        return appUtil.mapIdsToField("roomDatabase", "_id",roomIds, "roomName");
    }

}
