package com.example.demo01.core.Aws3.controller;

import com.example.demo01.core.Aws3.dtos.FileResponseDTO;
import com.example.demo01.core.Aws3.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/space/aws3")
@RequiredArgsConstructor
public class Aws3Controller {

    private final S3Service s3Service;

    @GetMapping("/download/file")
    public ResponseEntity<?> downloadFile(@RequestParam String fileName) {
        byte[] fileData = s3Service.downloadFile(fileName);
        return ResponseEntity.ok(fileData);
    }

    @PostMapping("/upload/file")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile fileData,
                                        @RequestParam("fileName") String fileName,
                                        @RequestParam("folderName") String folderName,
                                        @RequestParam(value = "isPublic", defaultValue = "false") Boolean isPublic) {
        FileResponseDTO fileUrl = s3Service.uploadFile(fileData, folderName, isPublic);
        return ResponseEntity.ok(fileUrl);
    }

}
