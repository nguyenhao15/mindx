package com.example.demo01.core.SpaceCustomer.controllers;

import com.example.demo01.configs.AppConstants;
import com.example.demo01.domains.MiniCrm.Contract.dtos.appendix.AppendixInfoDto;
import com.example.demo01.domains.MiniCrm.Contract.dtos.paymentCycles.PaymentCycleDTO;
import com.example.demo01.core.SpaceCustomer.models.CustomerAddress;
import com.example.demo01.core.SpaceCustomer.models.CustomerContact;
import com.example.demo01.core.SpaceCustomer.payload.CustomerInfo.CustomerInfoDTO;
import com.example.demo01.core.SpaceCustomer.payload.CustomerInfo.CustomerPatchRequestDTO;
import com.example.demo01.core.SpaceCustomer.payload.CustomerInfo.CustomerRequestDTO;
import com.example.demo01.core.SpaceCustomer.payload.CustomerInfo.CustomerResponse;
import com.example.demo01.core.SpaceCustomer.service.CustomerInfoService;
import com.example.demo01.domains.MiniCrm.Invoice.dto.InvoiceInfo;
import com.example.demo01.domains.MiniCrm.Process.dtos.ProcessingInfoDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.BatchMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/space/customer-info")
public class CustomerInfoController {

    private final CustomerInfoService customerInfoService;

    @GetMapping("/{customerCode}/validate")
    public ResponseEntity<?> validCustomerInfo(@PathVariable String customerCode) {
        Boolean isValid = customerInfoService.validCustomerInfo(customerCode);
        return ResponseEntity.ok(isValid);
    }

    @PostMapping
    public ResponseEntity<?> createNewCustomer(@Valid @RequestBody CustomerRequestDTO customerRequestDTO) {
        CustomerInfoDTO customerInfoDTO = customerInfoService.createCustomer(customerRequestDTO);
        return ResponseEntity.ok(customerInfoDTO);
    }

    @GetMapping
    public ResponseEntity<CustomerResponse> getAllCategories(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pagSize,
            @RequestParam(name = "sortBy", defaultValue = "customerTitle", required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder
    ) {
        CustomerResponse customerResponse = customerInfoService.getAllCustomer(pageNumber, pagSize, sortBy, sortOrder);
        return new ResponseEntity<>(customerResponse, HttpStatus.OK);
    }

    @GetMapping("/{customerCode}/code")
    public ResponseEntity<CustomerInfoDTO> findCustomerByCustomerCode(@PathVariable String customerCode) {
        return customerInfoService.findCustomerByCustomerCode(customerCode);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerInfoDTO> findCustomerByCustomerId(@PathVariable String id) {
        CustomerInfoDTO customerInfoDTO =  customerInfoService.findCustomerById(id);
        return new ResponseEntity<>(customerInfoDTO, HttpStatus.OK);
    }

    @GetMapping("/{customerTitle}/title")
    public ResponseEntity<CustomerResponse> getCustomerByContainingTitle(
            @PathVariable String customerTitle,
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pagSize,
            @RequestParam(name = "sortBy", defaultValue = "customerTitle", required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder
    ) {
        CustomerResponse customerResponse = customerInfoService.getAllCustomerByTittle(customerTitle, pageNumber, pagSize, sortBy, sortOrder);
        return new ResponseEntity<>(customerResponse, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CustomerInfoDTO> updateCustomerItem(
            @PathVariable String id,
            @Valid @RequestBody CustomerPatchRequestDTO patchRequestDTO
    ) {
        return ResponseEntity.ok(customerInfoService.updateCustomer(id, patchRequestDTO));
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<String> deleteCustomerItem(@PathVariable String customerId) {
        String deletedMessage = customerInfoService.deleteCustomerRelevantInfo(customerId);
        return new ResponseEntity<>(deletedMessage, HttpStatus.OK);
    }

    @PatchMapping("/{customerId}/address-line")
    public ResponseEntity<?> updateAddressLine(@PathVariable String customerId,
                                               @RequestBody CustomerAddress addressItem
    ) {
        CustomerInfoDTO customerInfoDTO = customerInfoService.updateCustomerAddress(customerId, addressItem);
        return new ResponseEntity<>(customerInfoDTO, HttpStatus.OK);
    }


    @DeleteMapping("/{customerId}/address-line/{addressId}")
    public ResponseEntity<?> deleteAddressLine(@PathVariable String customerId, @PathVariable String addressId) {
        CustomerInfoDTO customerInfoDTO = customerInfoService.deleteCustomerAddress(customerId, addressId);
        return new ResponseEntity<>(customerInfoDTO, HttpStatus.OK);
    }

    @PatchMapping("/{customerId}/contact-info")
    public ResponseEntity<?> updateContactInfo(@PathVariable String customerId,
                                               @RequestBody CustomerContact contactItem
    ) {
        CustomerInfoDTO customerInfoDTO = customerInfoService.updateCustomerContact(customerId, contactItem);
        return new ResponseEntity<>(customerInfoDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{customerId}/contact-info/{contactId}")
    public ResponseEntity<?> deleteContactInfo(@PathVariable String customerId, @PathVariable String contactId) {
        CustomerInfoDTO customerInfoDTO = customerInfoService.deleteCustomerContact(customerId, contactId);
        return new ResponseEntity<>(customerInfoDTO, HttpStatus.OK);
    }

    @GetMapping("/{keyword}/search")
    public ResponseEntity<?> searchCustomerInfo(@PathVariable String keyword) {
        List<CustomerInfoDTO> customerResponse = customerInfoService.searchCustomers(keyword);
        return new ResponseEntity<>(customerResponse, HttpStatus.OK);
    }

    @QueryMapping
    public CustomerInfoDTO getCustomerById(@Argument String id) {
         return customerInfoService.findCustomerById(id);
    }

    @BatchMapping(typeName = "AppendixInfoDto", field = "customerName")
    public Map<AppendixInfoDto, CustomerInfoDTO> customerName(List<AppendixInfoDto> appendices) {
        return customerInfoService.getBatchCustomers(appendices, AppendixInfoDto::getCustomerId);
    }

    @BatchMapping(typeName = "ProcessItem", field = "customerName")
    public Map<ProcessingInfoDto, CustomerInfoDTO> basementForProcess(List<ProcessingInfoDto> processingInfoDtos) {
        return customerInfoService.getBatchCustomers(processingInfoDtos, ProcessingInfoDto::getCustomerId);
    }

    @BatchMapping(typeName = "PaymentTerm", field = "customerName")
    public Map<PaymentCycleDTO, CustomerInfoDTO> customerForPaymentTerms(List<PaymentCycleDTO> paymentCycleDTOs) {
        return customerInfoService.getBatchCustomers(paymentCycleDTOs, PaymentCycleDTO::getCustomerId);
    }

    @BatchMapping(typeName = "Invoice", field = "customerName")
    public  Map<InvoiceInfo, CustomerInfoDTO> customerForInvoice(List<InvoiceInfo> invoiceInfoList) {
        return customerInfoService.getBatchCustomers(invoiceInfoList, InvoiceInfo::getCustomerId);
    }
}
