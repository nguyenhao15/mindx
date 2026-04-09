package com.example.demo01.core.Basement.controller;

import com.example.demo01.configs.AppConstants;
import com.example.demo01.core.Basement.dto.room.RoomDtoResponse;
import com.example.demo01.core.Basement.dto.room.RoomInfoDto;
import com.example.demo01.core.Basement.dto.room.RoomPatchRequest;
import com.example.demo01.core.Basement.dto.room.RoomRequestDto;
import com.example.demo01.core.Basement.service.RoomCollectionService;
import com.example.demo01.domains.mongo.MiniCrm.Renting.dtos.RentingDto;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.BatchMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rooms")
public class RoomController {

    private final RoomCollectionService roomCollectionService;

    @PostMapping
    public ResponseEntity<?> createRoom(@RequestBody RoomRequestDto requestDto) {
        RoomInfoDto roomInfoDto = roomCollectionService.createRoom(requestDto);
        return ResponseEntity.ok(roomInfoDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRoomById(@PathVariable String id) {
        RoomInfoDto roomInfoDto = roomCollectionService.getRoomById(id);
        return ResponseEntity.ok(roomInfoDto);
    }

    @GetMapping
    public ResponseEntity<?> getAllRoom(@RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
                                        @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pagSize,
                                        @RequestParam(name = "sortBy", defaultValue = "roomName", required = false) String sortBy,
                                        @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder) {

        RoomDtoResponse roomDtoResponse = roomCollectionService.getAllRoom(pageNumber, pagSize, sortBy, sortOrder);
        return ResponseEntity.ok(roomDtoResponse);
    }

    @GetMapping("/{buShortName}/bu-shortname")
    public ResponseEntity<?> getRoomByBuShortName(@PathVariable String buShortName,
                                                  @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
                                                  @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pagSize,
                                                  @RequestParam(name = "sortBy", defaultValue = "roomName", required = false) String sortBy,
                                                  @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder) {
        RoomDtoResponse roomDtoResponse = roomCollectionService.getRoomByBuShortName(buShortName, pageNumber, pagSize, sortBy, sortOrder);
        return ResponseEntity.ok(roomDtoResponse);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateBuById(@PathVariable String id,
                                          @RequestBody RoomPatchRequest roomPatchRequest) {
        RoomInfoDto roomInfoDto = roomCollectionService.updateRoomById(id, roomPatchRequest);
        return ResponseEntity.ok(roomInfoDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRoomById(@PathVariable String id) {
        String deleteMessage = roomCollectionService.deleteRoomById(id);
        return ResponseEntity.ok(deleteMessage);
    }

    @BatchMapping(typeName = "RentingDto", field = "roomName")
    public Map<RentingDto, RoomInfoDto> serviceNameForAppendix(List<RentingDto> rentings) {
        return roomCollectionService.getBatchRoomIds(rentings, RentingDto::getRoomShortName);
    }
}
