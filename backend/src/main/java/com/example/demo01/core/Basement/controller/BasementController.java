package com.example.demo01.core.Basement.controller;

import com.example.demo01.core.Basement.dto.basement.BUInfoDto;
import com.example.demo01.core.Basement.dto.basement.BUPatchRequestDto;
import com.example.demo01.core.Basement.dto.basement.BURequestDto;
import com.example.demo01.core.Basement.service.BasementService;
import com.example.demo01.domains.mongo.MiniCrm.Contract.dtos.appendix.AppendixInfoDto;
import com.example.demo01.domains.mongo.MiniCrm.Contract.dtos.paymentCycles.PaymentCycleDTO;
import com.example.demo01.domains.mongo.MiniCrm.Invoice.dto.InvoiceInfo;
import com.example.demo01.domains.mongo.MiniCrm.Process.dtos.ProcessingInfoDto;
import com.example.demo01.domains.mongo.MiniCrm.Renting.dtos.RentingDto;
import com.example.demo01.utils.FilterWithPagination;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.BatchMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bus")
public class BasementController {

    private final BasementService basementService;

    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('ADMIN')")
    public ResponseEntity<?> createNewBasement(@Valid @RequestBody BURequestDto buRequestDto) {
        BUInfoDto buInfoDto = basementService.createNewBu(buRequestDto);
        return ResponseEntity.ok(buInfoDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBuById(@PathVariable String id) {
        BUInfoDto buInfoDto = basementService.getBuInfoById(id);
        return ResponseEntity.ok(buInfoDto);
    }

    @PostMapping("/batch-create")
    public ResponseEntity<?> createBuItems(@Valid @RequestBody List<BURequestDto> buRequestDtos) {
        basementService.createBus(buRequestDtos);
        return ResponseEntity.ok("Batch created");
    }

    @PostMapping("/get-all")
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('ADMIN')")
    public ResponseEntity<?> getAllBu(@RequestBody FilterWithPagination filter) {
        return ResponseEntity.ok(basementService.getAllBU(filter));
    }

    @GetMapping("/active-bus")
    public ResponseEntity<?> getActiveBu() {
        List<BUInfoDto> buResponse = basementService.getActiveBuInfos();
        return ResponseEntity.ok(buResponse);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateById(@PathVariable String id,
                                        @RequestBody BUPatchRequestDto requestDto) {
        BUInfoDto buInfoDto = basementService.updateBUById(id, requestDto);
        return ResponseEntity.ok(buInfoDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteById(@PathVariable String id) {
        String deleteMessage = basementService.deleteBUById(id);
        return ResponseEntity.ok(deleteMessage);
    }

    @BatchMapping(typeName = "AppendixInfoDto", field = "buName")
    public Map<AppendixInfoDto, BUInfoDto> basementForAppendix(List<AppendixInfoDto> appendices) {
        return basementService.getBatchBuIds(appendices, AppendixInfoDto::getBuId);
    }

    @BatchMapping(typeName = "RentingDto", field = "buName")
    public Map<RentingDto, BUInfoDto> basementForRenting(List<RentingDto> appendices) {
        return basementService.getBatchBuIds(appendices, RentingDto::getBuId);
    }

    @BatchMapping(typeName = "ProcessItem", field = "buName")
    public Map<ProcessingInfoDto, BUInfoDto> basementForProcess(List<ProcessingInfoDto> processingInfoDtos) {
        return basementService.getBatchBuIds(processingInfoDtos, ProcessingInfoDto::getBuId);
    }

    @BatchMapping(typeName = "PaymentTerm", field = "buName")
    public Map<PaymentCycleDTO, BUInfoDto> basementForPaymentTerm(List<PaymentCycleDTO> paymentCycleDTOS) {
        return basementService.getBatchBuIds(paymentCycleDTOS, PaymentCycleDTO::getBuId);
    }

    @BatchMapping(typeName = "Invoice", field = "buName")
    public Map<InvoiceInfo, BUInfoDto> basementForInvoice(List<InvoiceInfo> invoiceInfoList) {
        return basementService.getBatchBuIds(invoiceInfoList, InvoiceInfo::getBuId);
    }

}
