package com.example.demo01.core.Aws3.service;

import com.example.demo01.core.Aws3.dtos.FileResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface S3Service {

    FileResponseDTO uploadFile(MultipartFile file, String folder, Boolean isPublic);

    byte[] downloadFile(String fileName);

    String getPresignedUrl(String fileName, Long expiration);

    Map<String, String> getPresignedUrlsForFiles(List<String> fileNames, Long expiration);

    String getFileUrl(String fileName, Long expiration);

    String deleteFile(String fileName);

    FileResponseDTO uploadContractFile(String documentId, MultipartFile file);

    FileResponseDTO uploadPaymentOrderAttachment(String documentId, MultipartFile file);


}
