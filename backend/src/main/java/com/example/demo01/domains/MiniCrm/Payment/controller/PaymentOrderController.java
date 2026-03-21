package com.example.demo01.domains.MiniCrm.Payment.controller;

import com.example.demo01.configs.AppConstants;
import com.example.demo01.domains.MiniCrm.Payment.dtos.paymentOrder.*;
import com.example.demo01.domains.MiniCrm.Payment.service.PaymentOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/space/payment-order")
public class PaymentOrderController {

    private final PaymentOrderService paymentOrderService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createPaymentOrder(@RequestPart("data") PaymentOrderRequest paymentOrderRequest,
                                                @RequestPart(value = "files") List<MultipartFile> files ) {
        PaymentOrderWithDetail paymentOrderWithDetail = paymentOrderService.createNewPaymentOrder(paymentOrderRequest,files );
        return ResponseEntity.ok(paymentOrderWithDetail);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPaymentOrderById(@PathVariable String id) {
        PaymentOrderWithDetail paymentOrderWithDetail = paymentOrderService.getPaymentOrderById(id);
        return ResponseEntity.ok(paymentOrderWithDetail);
    }

    @GetMapping
    public ResponseEntity<?> getAllPaymentOrder(@RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
                                                @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pagSize,
                                                @RequestParam(name = "sortBy", defaultValue = "paymentDate", required = false) String sortBy,
                                                @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder) {
        PaymentOrderResponse paymentOrderResponse = paymentOrderService.getAllPaymentOrder(pageNumber, pagSize, sortBy, sortOrder);
        return ResponseEntity.ok(paymentOrderResponse);
    }

    @GetMapping("/{confirmStatus}/confirm-status")
    public ResponseEntity<?> getPaymentOrderByConfirmStatus(@PathVariable String confirmStatus,
                                                            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
                                                            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pagSize,
                                                            @RequestParam(name = "sortBy", defaultValue = "paymentDate", required = false) String sortBy,
                                                            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder) {
        PaymentOrderResponse paymentOrderResponse = paymentOrderService.getUnConfirmedPaymentOrder(confirmStatus, pageNumber, pagSize, sortBy, sortOrder);
        return ResponseEntity.ok(paymentOrderResponse);
    }

    @GetMapping("/{customerId}/customer-id")
    public ResponseEntity<?> getPaymentOrderByCustomerId(@PathVariable String customerId,
                                                         @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
                                                         @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pagSize,
                                                         @RequestParam(name = "sortBy", defaultValue = "paymentDate", required = false) String sortBy,
                                                         @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder) {
        PaymentOrderResponse paymentOrderResponse = paymentOrderService.getPaymentOrderByTenantId(customerId, pageNumber, pagSize, sortBy, sortOrder);
        return ResponseEntity.ok(paymentOrderResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePaymentOrderById(@PathVariable String id,
                                                    @RequestBody PaymentOrderPatchRequest paymentOrderPatchRequest) {
        PaymentOrderDTO paymentOrderDTO = paymentOrderService.updatePaymentOrderById(id, paymentOrderPatchRequest);
        return ResponseEntity.ok(paymentOrderDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePaymentOrderById(@PathVariable String id) {
        String deleteMessage = paymentOrderService.deletePaymentOrderById(id);
        return ResponseEntity.ok(deleteMessage);
    }


}
