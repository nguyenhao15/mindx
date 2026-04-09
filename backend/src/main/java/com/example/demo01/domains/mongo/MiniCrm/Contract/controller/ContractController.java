package com.example.demo01.domains.mongo.MiniCrm.Contract.controller;

import com.example.demo01.configs.AppConstants;
import com.example.demo01.core.Aws3.dtos.FileResponseDTO;
import com.example.demo01.domains.mongo.MiniCrm.Contract.dtos.appendix.AppendixRequest;
import com.example.demo01.domains.mongo.MiniCrm.Contract.dtos.contracts.ContractDTO;
import com.example.demo01.domains.mongo.MiniCrm.Contract.dtos.contracts.ContractFullPayload;
import com.example.demo01.domains.mongo.MiniCrm.Contract.dtos.contracts.ContractPatchRequestDto;
import com.example.demo01.domains.mongo.MiniCrm.Contract.service.ContractServices;
import com.example.demo01.utils.BasePageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/space/contracts")
public class ContractController {

    private final ContractServices services;

    @PostMapping("/preview")
    public ResponseEntity<?> createPreviewContract(@Valid @RequestBody AppendixRequest dto) {
        ContractDTO contract = services.createPreviewContract(dto);
        return ResponseEntity.ok(contract);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getContractById(@PathVariable String id) {
        ContractFullPayload contractFullPayload = services.getContractById(id);
        return ResponseEntity.ok(contractFullPayload);
    }

    @GetMapping("/contractCode")
    public ResponseEntity<?> getContractByContractCode(@RequestParam String contractCode) {
        ContractFullPayload contractFullPayload = services.getContractByContractCode(contractCode);
        return ResponseEntity.ok(contractFullPayload);
    }

    @GetMapping
    public ResponseEntity<?> getAllContract(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pagSize,
            @RequestParam(name = "sortBy", defaultValue = "startDate", required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder
    ) {
        BasePageResponse<ContractDTO> contractResponse = services.getAllContract(pageNumber, pagSize, sortBy, sortOrder);
        return ResponseEntity.ok(contractResponse);
    }


    @GetMapping("/{contractStatus}/contract-status")
    public ResponseEntity<?> getContractByStatus(
            @PathVariable String contractStatus,
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pagSize,
            @RequestParam(name = "sortBy", defaultValue = "startDate", required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder
    ) {
        BasePageResponse<ContractDTO> contractResponse = services.getAllContractByStatus(contractStatus, pageNumber, pagSize, sortBy, sortOrder);
        return ResponseEntity.ok(contractResponse);
    }

    @GetMapping("/{customerId}/customer-id")
    public ResponseEntity<?> getContractsByCustomerId(
            @PathVariable String customerId,
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pagSize,
            @RequestParam(name = "sortBy", defaultValue = "startDate", required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder
    ) {
        BasePageResponse<ContractDTO> contractResponse = services.getAllContractByTenantId(customerId, pageNumber, pagSize, sortBy, sortOrder);
        return ResponseEntity.ok(contractResponse);
    }

    @GetMapping("/{buShortName}/bu-id")
    public ResponseEntity<?> getContractByBuShortName(
            @PathVariable String buShortName,
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pagSize,
            @RequestParam(name = "sortBy", defaultValue = "startDate", required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder
    ) {
        BasePageResponse<ContractDTO> contractResponse = services.getAllContractByBU(buShortName, pageNumber, pagSize, sortBy, sortOrder);
        return ResponseEntity.ok(contractResponse);
    }

    @PostMapping("/upload-contract-file/{id}")
    public ResponseEntity<?> updateContractFileById(@PathVariable String id,
                                                    @RequestBody MultipartFile fileUpdateRequestDto) {
        try {
            FileResponseDTO contractPath = services.uploadContractFile(id, fileUpdateRequestDto);
            return ResponseEntity.ok(contractPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateContractById(@PathVariable String id,
                                                @RequestBody ContractPatchRequestDto patchRequestDto) {
        ContractDTO contractDTO = services.updateContractById(id, patchRequestDto);
        return ResponseEntity.ok(contractDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteContractById(@PathVariable String id) {
        String deletedMessage = services.deleteContractById(id);
        return ResponseEntity.ok(deletedMessage);
    }

}
