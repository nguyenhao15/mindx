package com.example.demo01.core.Attachment.controller;

import com.example.demo01.core.Attachment.service.AttachmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/attachment")
public class AttachmentController {

    private final AttachmentService attachmentService;

    @GetMapping("/{processFlowId}")
    public ResponseEntity<?> getAttachmentsByProcessFlowId(@PathVariable String processFlowId) {
        return ResponseEntity.ok(attachmentService.getAttachmentByOwnerId(processFlowId));
    }


    @GetMapping("/file/{id}")
    public ResponseEntity<?> getAttachmentFile(@PathVariable String id,
                                               @RequestParam Long expirationTimeInSeconds) {
        String fileUrl = attachmentService.getPreUrl(id, expirationTimeInSeconds);
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(fileUrl))
                .build();
    }
}
