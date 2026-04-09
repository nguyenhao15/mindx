package com.example.demo01.domains.mongo.MiniCrm.Process.controller;

import com.example.demo01.core.Aws3.dtos.FileResponseDTO;
import com.example.demo01.domains.mongo.MiniCrm.Contract.dtos.appendix.AppendixInfoDto;
import com.example.demo01.domains.mongo.MiniCrm.Contract.dtos.appendix.AppendixRequestFullPayload;
import com.example.demo01.domains.mongo.MiniCrm.Process.dtos.ProcessingInfoDto;
import com.example.demo01.domains.mongo.MiniCrm.Process.dtos.ProcessingRequest;
import com.example.demo01.domains.mongo.MiniCrm.Process.service.ProcessingService;
import com.example.demo01.utils.BasePageResponse;
import com.example.demo01.utils.FilterWithPagination;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/space/processes")
@RequiredArgsConstructor
public class ProcessController {

    private final ProcessingService processingService;


    @SchemaMapping(typeName = "AppendixInfoDto" ,field =  "processItem")
    public ProcessingInfoDto processForAppendix(AppendixInfoDto appendixInfoDto) {
        return processingService.getProcessByCode(appendixInfoDto.getAppendixCode());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProcessById(@PathVariable String id) {
        ProcessingInfoDto processingInfoDto = processingService.getProcessById(id);
        return ResponseEntity.ok(processingInfoDto);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createProcess(@RequestPart(value = "data") @Valid AppendixRequestFullPayload requestFullPayload,
                                           @RequestPart(value = "file", required = false) MultipartFile file ) {
        ProcessingInfoDto processingInfoDto = processingService.createProcess(requestFullPayload, file);
        return ResponseEntity.ok(processingInfoDto);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, value = "/re-upload-file")
    public ResponseEntity<?> reUploadFileInProcess(@RequestPart(value = "processCode") String processCode,
                                                   @RequestPart(value = "file") MultipartFile file ) throws IOException {
        FileResponseDTO processingInfoDto = processingService.reUploadFileInProcess(processCode, file);
        return ResponseEntity.ok(processingInfoDto);
    }

    @GetMapping("/by-code/{processCode}")
    public ResponseEntity<?> getProcessByCode(@PathVariable String processCode) {
        ProcessingInfoDto processingInfoDto = processingService.getProcessByCode(processCode);
        return ResponseEntity.ok(processingInfoDto);
    }

    @PostMapping("/active")
    public ResponseEntity<?> getActiveProcesses(@RequestBody FilterWithPagination filterWithPagination) {
        BasePageResponse<ProcessingInfoDto> processingInfoDto = processingService.getActiveProcesses(filterWithPagination);
        return ResponseEntity.ok(processingInfoDto);
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<?> completeProcessById(@PathVariable String id) {
        ProcessingInfoDto processingInfoDto = processingService.completedProcess(id);
        return ResponseEntity.ok(processingInfoDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateProcess(@PathVariable String id,
                                           @RequestBody ProcessingRequest processingRequest) {
        ProcessingInfoDto processingInfoDto = processingService.updateProcess(id, processingRequest);
        return ResponseEntity.ok(processingInfoDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProcessById(@PathVariable String id) {
        String message = processingService.deleteProcessById(id);
        return ResponseEntity.ok(message);
    }



}
