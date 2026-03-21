package com.example.demo01.domains.MiniCrm.Dimmesion.controller;

import com.example.demo01.domains.MiniCrm.Contract.dtos.appendix.AppendixInfoDto;
import com.example.demo01.domains.MiniCrm.Contract.dtos.paymentCycles.PaymentCycleDTO;
import com.example.demo01.domains.MiniCrm.Dimmesion.dtos.Products.ProductInfoDto;
import com.example.demo01.domains.MiniCrm.Dimmesion.dtos.Products.ProductRequestDto;
import com.example.demo01.domains.MiniCrm.Dimmesion.service.ProductService;
import com.example.demo01.domains.MiniCrm.Invoice.dto.InvoiceInfo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.graphql.data.method.annotation.BatchMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/space/products")
public class ProductController {

    private final ProductService productService;

    private final CacheManager cacheManager;

    @PostMapping
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductRequestDto productRequestDto) {
        return productService.createProduct(productRequestDto);
    }

    @GetMapping
    public ResponseEntity<?> getAllProducts() {
        List<ProductInfoDto> productInfoDtos = productService.getAllProduct();
        return new ResponseEntity<>(productInfoDtos, HttpStatus.OK);
    }

    @BatchMapping(typeName = "AppendixInfoDto", field = "serviceName")
    public Map<AppendixInfoDto, ProductInfoDto> serviceNameForAppendix(List<AppendixInfoDto> appendices) {
        return productService.getBatchProducts(appendices, AppendixInfoDto::getServiceId);
    }

    @BatchMapping(typeName = "PaymentTerm", field = "serviceName")
    public Map<PaymentCycleDTO, ProductInfoDto> serviceNameForPaymentTerms(List<PaymentCycleDTO> paymentCycles) {
        return productService.getBatchProducts(paymentCycles, PaymentCycleDTO::getServiceId);
    }

    @BatchMapping(typeName = "Invoice", field = "productName")
    public Map<InvoiceInfo, ProductInfoDto> productNameForInvoices(List<InvoiceInfo> invoices) {
        return productService.getBatchProducts(invoices, InvoiceInfo::getServiceId);
    }

}
