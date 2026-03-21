package com.example.demo01.domains.MiniCrm.Renting.controller;

import com.example.demo01.configs.AppConstants;
import com.example.demo01.core.Basement.dto.room.RoomInfoDto;
import com.example.demo01.domains.MiniCrm.Contract.dtos.appendix.AppendixInfoDto;
import com.example.demo01.core.SpaceCustomer.payload.CustomerInfo.CustomerInfoDTO;
import com.example.demo01.domains.MiniCrm.Renting.dtos.RentingDto;
import com.example.demo01.domains.MiniCrm.Renting.dtos.RentingPatchRequest;
import com.example.demo01.domains.MiniCrm.Renting.dtos.RentingRequest;
import com.example.demo01.domains.MiniCrm.Renting.dtos.RentingResponse;
import com.example.demo01.domains.MiniCrm.Renting.service.RentingContractService;
import com.example.demo01.utils.PageInput;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/space/renting")
public class RentingController {

    private final RentingContractService rentingContractService;

    @PostMapping
    public ResponseEntity<?> createRentingItem(@Valid @RequestBody RentingRequest request) {
        RentingDto rentingDto = rentingContractService.createRentingRequest(request);
        return ResponseEntity.ok(rentingDto);
    }

    @GetMapping
    public ResponseEntity<?> getAllRentingItem(@RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
                                               @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pagSize,
                                               @RequestParam(name = "sortBy", defaultValue = "assigned_from", required = false) String sortBy,
                                               @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder) {
        RentingResponse rentingResponse = rentingContractService.getAllRenting(pageNumber, pagSize, sortBy, sortOrder);
        return ResponseEntity.ok(rentingResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRentingById(@PathVariable String id) {
        RentingDto rentingDto = rentingContractService.getRentingById(id);
        return ResponseEntity.ok(rentingDto);
    }

    @GetMapping("/{status}/status")
    public ResponseEntity<?> getRentingDefault(@PathVariable String status,
                                               @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
                                               @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pagSize,
                                               @RequestParam(name = "sortBy", defaultValue = "assigned_from", required = false) String sortBy,
                                               @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder) {
        RentingResponse rentingResponse = rentingContractService.getRentingByDefault(status, pageNumber, pagSize, sortBy, sortOrder);
        return ResponseEntity.ok(rentingResponse);
    }

    @GetMapping("/{contractId}/contract-id")
    public ResponseEntity<?> getRentingByContractId(@PathVariable String contractId) {
        List<RentingDto> rentingDtoList = rentingContractService.getRentingByContractId(contractId);
        return ResponseEntity.ok(rentingDtoList);
    }

    @GetMapping("/{buShortName}/bu-id")
    public ResponseEntity<?> getRentingByBuShortName(@PathVariable String buShortName,
                                                     @RequestParam PageInput pageInput) {
        RentingResponse rentingResponse = rentingContractService.getRentingByBuShortName(buShortName, pageInput);
        return ResponseEntity.ok(rentingResponse);
    }

    @GetMapping("/{customerId}/customer-id")
    public ResponseEntity<?> getRentingByCustomerId(@PathVariable String customerId){
        List<RentingDto> rentingResponse = rentingContractService.getRentingByCustomerId(customerId);
        return ResponseEntity.ok(rentingResponse);
    }

    @GetMapping("/available/{buShortName}")
    public ResponseEntity<?> getAvailableRoomByBuShortName(@PathVariable String buShortName,
                                                           @RequestParam(name = "roomIds", required = false) List<String> roomIds) {
        List<RoomInfoDto> roomInfoDtos = rentingContractService.getAvailableRoomForRenting(buShortName, roomIds);
        return  ResponseEntity.ok(roomInfoDtos);
    }

    @GetMapping("/active/renting/{buShortName}")
    public ResponseEntity<?> getActiveRentingByBu(@PathVariable String buShortName) {
        List<RentingDto> rentingDtoList = rentingContractService.getActiveRentingByBuShortName(buShortName);
        return ResponseEntity.ok(rentingDtoList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRentingById(@PathVariable String id,
                                               @RequestBody RentingPatchRequest request) {
        RentingDto updatedRenting = rentingContractService.updateRentingById(id, request);
        return ResponseEntity.ok(updatedRenting);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRentingItemById(@PathVariable String id) {
        String deletedMessage = rentingContractService.deleteRentingById(id);
        return ResponseEntity.ok(deletedMessage);
    }

    @SchemaMapping(typeName = "AppendixInfoDto", field = "rentingItems")
    public List<RentingDto> rentingForAppendix(AppendixInfoDto appendixInfoDto) {
        return rentingContractService.getBatchRentingByContractCode(appendixInfoDto.getContractCode());
    }


    @SchemaMapping(typeName = "Customer", field = "rentalList")
    public List<RentingDto> rentingForCustomer(CustomerInfoDTO customerInfoDTO) {
        return rentingContractService.getRentingByCustomerId(customerInfoDTO.get_id());
    }


}
