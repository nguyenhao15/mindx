package com.example.demo01.domains.ProcessManagemnet.ProcessFlow.controller;

import com.example.demo01.domains.ProcessManagemnet.ProcessFlow.service.ProcessFlowTextContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/process/process-flows-content")
@RequiredArgsConstructor
public class ProcessFlowTextContentController {
    private final ProcessFlowTextContentService processFlowTextContentService;

    @GetMapping("/process-flow-id/{processFlowId}")
    public ResponseEntity<?> getTextContentByProcessFlowId(@PathVariable String processFlowId) {
        return ResponseEntity.ok(processFlowTextContentService.getProcessFlowTextContent(processFlowId));
    }

    @PostMapping(value = "/upload-file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadFileInLineEditor(@RequestParam("file") MultipartFile fileData) {
        return ResponseEntity.ok(processFlowTextContentService.uploadFileInTextEditor(fileData));
    }

}
