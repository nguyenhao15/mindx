package com.example.demo01.core.Aws3.service;

import com.example.demo01.configs.Constants.CacheConstants;
import com.example.demo01.core.Aws3.dtos.FileResponseDTO;
import com.example.demo01.core.Exceptions.APIException;
import com.example.demo01.utils.AppUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class S3ServiceImpl implements S3Service  {

    private final S3Presigner resigned;

    private final S3Client s3Client;

    private final AppUtil appUtil;

    @Value("${aws.s3.bucketName}")
    private String bucketName;

    @Value("${aws.region}")
    private String region;

    @Override
    public FileResponseDTO uploadFile(MultipartFile file, String folder, Boolean isPublic) {
        String originalName = file.getOriginalFilename();

        String urlName;
        String extension = "";

        if (originalName != null && originalName.contains(".")) {
            extension = appUtil.handleGetFileExtension(originalName);
        }

        String uniqueId = appUtil.generateFileKey();

        if (!uniqueId.toLowerCase().endsWith(extension.toLowerCase())) {
            uniqueId += extension;
        }

        String urlFolder = isPublic ? "/public/" : "/";
        String handleFolderName = "MINDX/" + folder + urlFolder + uniqueId;

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(handleFolderName)
                .contentType(file.getContentType())
                .build();
        FileResponseDTO fileResponseDTO = new FileResponseDTO();
        try {
            s3Client.putObject(putObjectRequest,
                    RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (isPublic) {
            urlName = String.format("https://%s.s3.%s.amazonaws.com/%s",
                    bucketName,region, handleFolderName);
        } else {
            urlName = uniqueId;
        }

        fileResponseDTO.setFileName(handleFolderName);
        fileResponseDTO.setContentType(file.getContentType());
        fileResponseDTO.setSize(file.getSize());
        fileResponseDTO.setFileUrl(urlName);
        return fileResponseDTO;
    }

    @Override
    public byte[] downloadFile(String fileName) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build();
        ResponseBytes<GetObjectResponse> objectBytes = s3Client.getObject(
                getObjectRequest,
                ResponseTransformer.toBytes()
        );

        return objectBytes.asByteArray();
    }

    @Cacheable(value = CacheConstants.AWS3_CACHE, key = "#fileName", sync = true)
    @Override
    public String getPresignedUrl(String fileName, Long expiration) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build();

        GetObjectPresignRequest generatePresignedUrlRequest = GetObjectPresignRequest.builder()
                .getObjectRequest(getObjectRequest)
                .signatureDuration(Duration.ofMinutes(expiration))
                .build();

        return resigned.presignGetObject(generatePresignedUrlRequest).url().toString();
    }

    @Override
    public Map<String, String> getPresignedUrlsForFiles(List<String> fileNames, Long expiration) {
        return fileNames.parallelStream()
                .collect(Collectors.toConcurrentMap(
                        name -> name,
                        name -> getPresignedUrl(name, expiration)
                ));
    }

    @Override
    public String getFileUrl(String fileName, Long expiration) {
        return getPresignedUrl(fileName, expiration);
    }

    @Override
    public String deleteFile(String fileName) {
        s3Client.deleteObject(builder -> builder.bucket(bucketName).key(fileName));
        return fileName;
    }

    @Override
    public FileResponseDTO uploadContractFile(String documentId, MultipartFile file) {
        try {
            String handleContractCodeToValidName = documentId.replace("/","_");
            String fileName = handleContractCodeToValidName + "_" + appUtil.handleSubString(UUID.randomUUID().toString(),5,true);
            return uploadFile(file, "SPACE/CONTRACT",false);
        } catch (Exception e) {
            throw new APIException("Failed to upload contract file: " + e.getMessage());
        }
    }

    @Override
    public FileResponseDTO uploadPaymentOrderAttachment(String documentId, MultipartFile file) {
        try {
            String handleNameAttachment = documentId.replace("/","_");
            String fileName = handleNameAttachment + "_" + appUtil.handleSubString(UUID.randomUUID().toString(),5,true);
            return uploadFile(file, "SPACE/PAYMENT",false);
        } catch (Exception e) {
            throw new  APIException("Failed to upload contract file: " + e.getMessage());
        }
    }
}
