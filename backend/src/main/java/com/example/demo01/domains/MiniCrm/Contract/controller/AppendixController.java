package com.example.demo01.domains.MiniCrm.Contract.controller;

import com.example.demo01.core.Aws3.dtos.FileResponseDTO;
import com.example.demo01.domains.MiniCrm.Contract.dtos.appendix.AppendixInfoDto;
import com.example.demo01.domains.MiniCrm.Contract.dtos.appendix.AppendixRequest;
import com.example.demo01.domains.MiniCrm.Contract.dtos.appendix.AppendixRequestFullPayload;
import com.example.demo01.domains.MiniCrm.Contract.dtos.appendix.AppendixResponseFullPayload;
import com.example.demo01.domains.MiniCrm.Contract.dtos.contractUtils.CalPriceRequest;
import com.example.demo01.domains.MiniCrm.Contract.dtos.contractUtils.CalTotalContractValueRequest;
import com.example.demo01.domains.MiniCrm.Contract.service.AppendixService;
import com.example.demo01.domains.MiniCrm.Contract.service.ContractUtilService;
import com.example.demo01.core.SpaceCustomer.payload.CustomerInfo.CustomerInfoDTO;
import com.example.demo01.domains.MiniCrm.Process.dtos.ProcessingInfoDto;
import com.example.demo01.utils.BasePageResponse;
import com.example.demo01.utils.FilterWithPagination;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/space/appendix")
public class AppendixController {

    private final ContractUtilService contractUtilService;

    private final AppendixService appendixService;


    @QueryMapping
    public AppendixInfoDto getAppendixById(@Argument String id) {
        return appendixService.getAppendixInfoInGraphById(id);
    }


    @SchemaMapping(typeName = "ProcessItem", field = "appendixInfo")
    public AppendixInfoDto appendixForProcess(ProcessingInfoDto processingInfoDto) {
        return appendixService.getAppendixByAppendixCode(processingInfoDto.getProcessCode());
    }

    @SchemaMapping(typeName = "Customer", field = "appendixList")
    public List<AppendixInfoDto> getAppendixByCustomerId(CustomerInfoDTO customerInfoDTO) {
        return appendixService.getAppendixByCustomerId(customerInfoDTO.get_id());
    }

    @PostMapping("/calculate-monthly-price")
    public ResponseEntity<?> MonthlyPriceCalculation(@Valid @RequestBody CalPriceRequest dto) {
        Double monthlyPrice = contractUtilService.calculatePrice(dto);
        return ResponseEntity.ok(monthlyPrice);
    }

    @PostMapping("/calculate-total-value")
    public ResponseEntity<?> totalContractValueCalculation(@Valid @RequestBody CalTotalContractValueRequest dto) {
        Double totalContractValue = contractUtilService.calculateTotalValue(dto);
        return ResponseEntity.ok(totalContractValue);
    }

    @PostMapping("/preview")
    public ResponseEntity<?> previewAppendix(@Valid @RequestBody AppendixRequest appendixRequest ) {
        AppendixResponseFullPayload appendixResponseFullPayload = appendixService.previewAppendix(appendixRequest);
        return  ResponseEntity.ok(appendixResponseFullPayload);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createNewAppendix(@RequestPart(value = "data") @Valid AppendixRequestFullPayload appendixRequestFullPayload,
                                               @RequestPart(value = "file", required = false) MultipartFile file ) {
        AppendixResponseFullPayload appendixResponseFullPayload = appendixService.createAppendix(appendixRequestFullPayload, file);
        return ResponseEntity.ok(appendixResponseFullPayload);
    }

    @PostMapping("/filter/search")
        public ResponseEntity<?> filterAppendix(@RequestBody FilterWithPagination filterWithPagination) {
        BasePageResponse<AppendixInfoDto> response = appendixService.getActiveAppendix(filterWithPagination.getPagination(), filterWithPagination.getFilters());
        return ResponseEntity.ok(response);
    }


    @PutMapping("/{appendixId}/upload-file")
    public ResponseEntity<?> uploadAppendixFile(@PathVariable String appendixId,
                                               @RequestPart("file") MultipartFile file ) throws Exception {
        FileResponseDTO fileResponseDTO = appendixService.uploadAppendixFile(appendixId, file);
        return ResponseEntity.ok(fileResponseDTO);
    }

    @GetMapping("/customer-id/{customerId}")
    public ResponseEntity<?> getAppendixByCustomerId(@PathVariable String customerId) {
        return ResponseEntity.ok(appendixService.getAppendixByCustomerId(customerId));
    }

    @PostMapping("/{appendixId}/add-payment")
    public ResponseEntity<?> addPaymentToAppendix(@PathVariable String appendixId,
                                                  @RequestParam Double paidAmount,
                                                  @RequestParam Double exchangeRate) {
        AppendixResponseFullPayload response = appendixService.handlePaymentAppendix(appendixId, paidAmount, exchangeRate);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/contract-code/{contractCode}")
    public ResponseEntity<?> getAppendixByContractCode(@PathVariable String contractCode) {
        return ResponseEntity.ok(appendixService.getAppendixByContract(contractCode));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateAppendix(@PathVariable String id,
                                              @RequestBody @Valid AppendixRequest appendixRequest) {
        AppendixInfoDto response = appendixService.updateAppendix(id, appendixRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/active/customer-id")
    public ResponseEntity<?> getActiveAppendixByCustomerIdAndBuId(@RequestParam String customerId,
                                                                  @RequestParam String buId) {
        List<AppendixInfoDto> appendixInfoList = appendixService.getActiveAppendixByCustomerId(customerId, buId);
        return  ResponseEntity.ok(appendixInfoList);
    }

    @GetMapping("/{appendixId}")
    public ResponseEntity<?> getAppendixByIdInRest(@PathVariable String appendixId) {
        AppendixResponseFullPayload appendixResponseFullPayload = appendixService.getAppendixById(appendixId);
        return  ResponseEntity.ok(appendixResponseFullPayload);
    }

    @DeleteMapping("/{appendixId}")
    public ResponseEntity<?> deleteAppendix(@PathVariable String appendixId) {
        appendixService.deleteAppendix(appendixId);
        return ResponseEntity.ok("Appendix deleted successfully");
    }

    @GetMapping("/active/payment")
    public ResponseEntity<?> getAppendixByBuShortNameAndServiceId(@RequestParam String buShortName,
                                                                  @RequestParam String serviceId,
                                                                  @RequestParam String customerId) {
        List<AppendixInfoDto> appendixInfoDtoList = appendixService.getActiveAppendixByBuShortNameAndServiceIdAndCustomerId(buShortName, serviceId, customerId);
        return  ResponseEntity.ok(appendixInfoDtoList);
    }
}
