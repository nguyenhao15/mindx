package com.example.demo01.domains.mongo.MiniCrm.Payment.controller;

import com.example.demo01.domains.mongo.MiniCrm.Contract.dtos.appendix.AppendixInfoDto;
import com.example.demo01.core.SpaceCustomer.payload.CustomerInfo.CustomerInfoDTO;
import com.example.demo01.domains.mongo.MiniCrm.Payment.dtos.transaction.TransactionInfoDTO;
import com.example.demo01.domains.mongo.MiniCrm.Payment.dtos.transaction.TransactionPatchRequest;
import com.example.demo01.domains.mongo.MiniCrm.Payment.dtos.transaction.TransactionRequest;
import com.example.demo01.domains.mongo.MiniCrm.Payment.service.TransactionService;
import com.example.demo01.utils.BasePageResponse;
import com.example.demo01.utils.FilterWithPagination;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/space/payment-detail")
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/draft")
    public ResponseEntity<?> addPaymentDetailDraft(@Valid @RequestBody TransactionRequest input) {
        TransactionInfoDTO transactionInfoDTO = transactionService.addPaymentDetail(input);
        return ResponseEntity.ok(transactionInfoDTO);
    }

    @PostMapping
    public ResponseEntity<?> createPaymentDetail(@Valid @RequestBody TransactionRequest input) {
        TransactionInfoDTO transactionInfoDTO = transactionService.createAPaymentDetail(input);
        return ResponseEntity.ok(transactionInfoDTO);
    }

    @GetMapping("/{paymentOrderId}/payment-order")
    public ResponseEntity<?> getPaymentDetailByPaymentOrder(@PathVariable String paymentOrderId) {
        List<TransactionInfoDTO> transactionInfoDTOS = transactionService.getPaymentDetailByPaymentOrderId(paymentOrderId);
        return ResponseEntity.ok(transactionInfoDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPaymentDetailById(@PathVariable String id) {
        TransactionInfoDTO transactionInfoDTO = transactionService.getPaymentDetailById(id);
        return ResponseEntity.ok(transactionInfoDTO);
    }

    @PostMapping("/filter")
    public ResponseEntity<?> getAllPaymentDetail(@RequestBody FilterWithPagination filterWithPagination) {
        BasePageResponse<TransactionInfoDTO> transactionResponse = transactionService.getAllPaymentDetail(filterWithPagination);
        return ResponseEntity.ok(transactionResponse);
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updatePaymentDetailById(@PathVariable String id,
                                                     @RequestBody TransactionPatchRequest transactionPatchRequest) {
        TransactionInfoDTO transactionInfoDTO = transactionService.updatePaymentDetail(id, transactionPatchRequest);
        return ResponseEntity.ok(transactionInfoDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable String id) {
        String successfullyMessage = transactionService.deletePaymentDetail(id);
        return ResponseEntity.ok(successfullyMessage);
    }

    @SchemaMapping(typeName = "AppendixInfoDto", field = "transactions")
    public List<TransactionInfoDTO> transactionsForAppendix(AppendixInfoDto appendixInfoDto) {
        return transactionService.getTransactionsByPaymentId(appendixInfoDto.getAppendixCode());
    }

    @SchemaMapping(typeName = "Customer", field = "transactions")
    public List<TransactionInfoDTO> transactionInfoForCustomer(CustomerInfoDTO customerInfoDTO) {
        return transactionService.getPaymentDetailByCustomerId(customerInfoDTO.get_id());
    }

}
