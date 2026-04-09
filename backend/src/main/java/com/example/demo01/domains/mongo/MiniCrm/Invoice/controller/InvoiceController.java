package com.example.demo01.domains.mongo.MiniCrm.Invoice.controller;

import com.example.demo01.domains.mongo.MiniCrm.Contract.dtos.paymentCycles.PaymentCycleDTO;
import com.example.demo01.domains.mongo.MiniCrm.Invoice.dto.InvoiceActionResponse;
import com.example.demo01.domains.mongo.MiniCrm.Invoice.dto.InvoicePatchRequest;
import com.example.demo01.domains.mongo.MiniCrm.Invoice.dto.InvoiceRequest;
import com.example.demo01.domains.mongo.MiniCrm.Invoice.model.InvoiceStatus;
import com.example.demo01.utils.FilterRequest;
import com.example.demo01.domains.mongo.MiniCrm.Invoice.dto.InvoiceInfo;
import com.example.demo01.domains.mongo.MiniCrm.Invoice.service.InvoiceService;
import com.example.demo01.utils.BasePageResponse;
import com.example.demo01.utils.FilterWithPagination;
import com.example.demo01.utils.PageInput;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/space/invoice")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;

    @GetMapping("/active-list/{customerId}")
    public ResponseEntity<?> getActiveInvoiceByCustomerId(@PathVariable String customerId) {
        List<InvoiceInfo> invoiceInfoList = invoiceService.getActiveInvoiceByCustomerId(customerId);
        return ResponseEntity.ok(invoiceInfoList);
    }

    @PostMapping("/active/{active}")
    public ResponseEntity<?> getActiveInvoice(@PathVariable Boolean active,
                                              @RequestBody FilterWithPagination filterWithPagination) {
        List<FilterRequest> filterRequest = filterWithPagination.getFilters();
        PageInput pageInput = filterWithPagination.getPagination();
        BasePageResponse<InvoiceInfo> invoiceResponse = invoiceService.getProcessInvoice(active, filterRequest, pageInput);
        return ResponseEntity.ok(invoiceResponse);
    }

    @PostMapping("/create/by-request")
    public ResponseEntity<?> createInvoiceByRequest(@RequestBody InvoiceRequest invoiceRequest) {
        InvoiceInfo invoiceInfo = invoiceService.createInvoiceByRequest(invoiceRequest);
        return ResponseEntity.ok(invoiceInfo);
    }

    @PostMapping("/create/by-payment-term")
    public ResponseEntity<?> createInvoiceByPaymentTerm(@RequestBody PaymentCycleDTO paymentCycleDTO) {
        InvoiceInfo invoiceInfo = invoiceService.createInvoiceByPaymentTerm(paymentCycleDTO);
        return ResponseEntity.ok(invoiceInfo);
    }

    @GetMapping("/available-actions/{currentStatus}")
    public ResponseEntity<?> getAvailableActions(@PathVariable InvoiceStatus currentStatus) {
        List<InvoiceActionResponse> availableActions = invoiceService.getAvailableActions(currentStatus);
        return ResponseEntity.ok(availableActions);
    }

    @PatchMapping("/{invoiceId}")
    public ResponseEntity<?> updateInvoice(@PathVariable String invoiceId,
                                           @RequestBody InvoicePatchRequest invoiceRequest
    ) {
        InvoiceInfo updatedInvoice = invoiceService.updateInvoice(invoiceId, invoiceRequest);
        return ResponseEntity.ok(updatedInvoice);
    }
}
