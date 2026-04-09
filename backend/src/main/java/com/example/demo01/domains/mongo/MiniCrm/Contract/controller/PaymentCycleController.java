package com.example.demo01.domains.mongo.MiniCrm.Contract.controller;

import com.example.demo01.configs.AppConstants;
import com.example.demo01.domains.mongo.MiniCrm.Contract.dtos.appendix.AppendixInfoDto;
import com.example.demo01.domains.mongo.MiniCrm.Contract.dtos.paymentCycles.PaymentCyclePatchRequestDto;
import com.example.demo01.utils.FilterRequest;
import com.example.demo01.domains.mongo.MiniCrm.Contract.dtos.paymentCycles.PaymentCycleBulkGen;
import com.example.demo01.domains.mongo.MiniCrm.Contract.dtos.paymentCycles.PaymentCycleDTO;
import com.example.demo01.domains.mongo.MiniCrm.Contract.service.PaymentCycleService;
import com.example.demo01.utils.BasePageResponse;
import com.example.demo01.utils.FilterWithPagination;
import com.example.demo01.utils.PageInput;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/space/paymentCycles")
@RequiredArgsConstructor
public class PaymentCycleController {

    private final PaymentCycleService paymentCycleService;

    @GetMapping
    public ResponseEntity<?> getAllPaymentCycle(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pagSize,
            @RequestParam(name = "sortBy", defaultValue = "cycleDueDate", required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder
    ) {
        BasePageResponse<PaymentCycleDTO> paymentCycleResponse = paymentCycleService.getAllPaymentCycle(pageNumber, pagSize, sortBy, sortOrder);
        return ResponseEntity.ok(paymentCycleResponse);
    }

    @PostMapping("/process/{done}")
    public ResponseEntity<?> getActivePaymentCycle(@PathVariable Boolean done,
                                                   @RequestBody FilterWithPagination filterWithPagination) {
        List<FilterRequest> filterRequests = filterWithPagination.getFilters();
        PageInput pageInput = filterWithPagination.getPagination();
        BasePageResponse<PaymentCycleDTO> paymentCycleResponse = paymentCycleService.getActivePaymentCycle(done, pageInput, filterRequests);
        return ResponseEntity.ok(paymentCycleResponse);
    }

    @PostMapping("/invoice-process/{done}")
    public ResponseEntity<?> getInvoiceProcess(@PathVariable Boolean done,
                                               @RequestBody FilterWithPagination filterWithPagination) {
        List<FilterRequest> filterRequests = filterWithPagination.getFilters();
        PageInput pageInput = filterWithPagination.getPagination();
        BasePageResponse<PaymentCycleDTO> paymentCycleResponse = paymentCycleService.getPaymentCycleForInvoiceProcess(done, filterRequests, pageInput);
        return ResponseEntity.ok(paymentCycleResponse);
    }

    @GetMapping("/{buShortName}/bu-id")
    public ResponseEntity<?> getPaymentCycleByBu(
            @PathVariable String buShortName,
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pagSize,
            @RequestParam(name = "sortBy", defaultValue = "cycleDueDate", required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder
    ) {
        BasePageResponse<PaymentCycleDTO> paymentCycleResponse = paymentCycleService.getPaymentCycleByBu(buShortName, pageNumber, pagSize, sortBy, sortOrder);
        return ResponseEntity.ok(paymentCycleResponse);
    }

    @PostMapping("/generate-preview")
    public ResponseEntity<?> generatePaymentCyclePreview(@RequestBody @Valid PaymentCycleBulkGen paymentCycleBulkGen) {
        var paymentCycleDTOList = paymentCycleService.handleGeneratePaymentCyclePreview(paymentCycleBulkGen);
        return ResponseEntity.ok(paymentCycleDTOList);
    }

    @GetMapping("/payment/remain-amount")
    public ResponseEntity<?> getRemainPaymentByAppendixCode(@RequestParam String appendixCode) {
        Double remainValue = paymentCycleService.remainingPaymentByAppendixId(appendixCode);
        return  ResponseEntity.ok(remainValue);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updatePaymentCycle(@PathVariable String id,
                                                @RequestBody PaymentCyclePatchRequestDto patchRequestDto) {
        PaymentCycleDTO updatedPaymentCycle = paymentCycleService.updatePaymentCycle(id, patchRequestDto);
        return ResponseEntity.ok(updatedPaymentCycle);
    }

    @SchemaMapping(typeName = "AppendixInfoDto", field = "paymentTerms")
    public List<PaymentCycleDTO> paymentTermsForAppendix(AppendixInfoDto appendixInfoDto) {
        return paymentCycleService.getPaymentCycleByAppendixId(appendixInfoDto.getAppendixCode());
    }
}
