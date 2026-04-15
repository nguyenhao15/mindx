package com.example.demo01.core.Attachment.service.Impl;

import com.example.demo01.core.Attachment.dto.AttachmentDto;
import com.example.demo01.core.Attachment.mapper.AttachmentMapper;
import com.example.demo01.core.Attachment.model.AttachmentItem;
import com.example.demo01.repository.mongo.CoreRepo.AttachmentRepository.AttachmentRepository;
import com.example.demo01.core.Attachment.service.AttachmentService;
import com.example.demo01.core.Aws3.dtos.FileResponseDTO;
import com.example.demo01.core.Aws3.service.S3Service;
import com.example.demo01.core.Exceptions.ResourceNotFoundException;
import com.example.demo01.utils.AppUtil;
import com.example.demo01.utils.ModuleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttachmentServiceImpl implements AttachmentService {

    private final AttachmentRepository attachmentRepository;

    private final S3Service s3Service;

    private final AttachmentMapper attachmentMapper;

    private final AppUtil appUtil;

    @Override
    public List<AttachmentDto> addAttachment(List<MultipartFile> files, String ownerId, String pathName, ModuleEnum moduleEnum, Boolean isPublic) {

        List<AttachmentItem> attachmentItems = new ArrayList<>();

        for (MultipartFile file : files) {
            String fileName = appUtil.handleGetFileNameWithoutExtension(file.getOriginalFilename());

            FileResponseDTO attachment = s3Service.uploadFile(file,pathName,isPublic);

            AttachmentItem attachmentItem = new AttachmentItem();

            attachmentItem.setOwnerId(ownerId);
            attachmentItem.setPathName(attachment.getFileName());
            attachmentItem.setFileSize(file.getSize());
            attachmentItem.setFileName(fileName);
            attachmentItem.setModule(moduleEnum);
            attachmentItem.setFileType(file.getContentType());
            attachmentItem.setIsPublic(isPublic);
            attachmentItem.setFileUrl(attachment.getFileUrl());
            attachmentItem.setIsDeleted(false);
            attachmentItems.add(attachmentItem);
        }
        List<AttachmentItem> savedAttachments = attachmentRepository.saveAll(attachmentItems);

        return attachmentMapper.mapToAttachmentItemDto(savedAttachments);
    }

    @Override
    public List<AttachmentDto> getAttachmentByOwnerId(String ownerId) {
        List<AttachmentItem> attachmentItems = attachmentRepository.findByOwnerIdAndIsDeleted(ownerId, false);
        if (attachmentItems.isEmpty()) {
            return List.of();
        }
        return attachmentMapper.mapToAttachmentItemDto(attachmentItems);
    }

    @Override
    public AttachmentDto createAttachment(MultipartFile file, String ownerId, String folderName, Boolean isPublic) {
        String fileName = appUtil.handleGetFileNameWithoutExtension(file.getOriginalFilename());
        FileResponseDTO fileResponseDTO = s3Service.uploadFile(file, folderName, isPublic);
        AttachmentItem attachmentItem = new AttachmentItem();
        String fileUrlValue = isPublic ? fileResponseDTO.getFileUrl() : fileResponseDTO.getFileName();

        attachmentItem.setFileName(fileName);
        attachmentItem.setOwnerId(ownerId);
        attachmentItem.setPathName(fileResponseDTO.getFileName());
        attachmentItem.setFileSize(file.getSize());
        attachmentItem.setFileType(file.getContentType());
        attachmentItem.setFileUrl(fileUrlValue);
        attachmentItem.setIsDeleted(false);
        attachmentItem.setIsPublic(isPublic);

        AttachmentItem savedAttachment = attachmentRepository.save(attachmentItem);

        return attachmentMapper.mapToAttachmentDto(savedAttachment);
    }

    @Override
    public AttachmentItem getAttachmentItemById(String id) {
        return attachmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AttachmentItem","_id", id));
    }

    @Override
    public List<AttachmentItem> getDeletedAttachments() {
        return attachmentRepository.findByIsDeleted(true);
    }

    @Override
    public AttachmentDto getAttachmentById(String id) {
        AttachmentItem attachmentItem = getAttachmentItemById(id);
        return attachmentMapper.mapToAttachmentDto(attachmentItem);
    }

    @Override
    public String getPreUrl(String id , Long expirationTimeInSeconds) {
        AttachmentItem attachmentItem = getAttachmentItemById(id);
        if (attachmentItem.getIsPublic()) {
                return attachmentItem.getFileUrl();
        } else {
                return s3Service.getPresignedUrl(attachmentItem.getPathName(), expirationTimeInSeconds);
        }
    }

    @Override
    public void permanentlyDeleteAttachment() {
        List<AttachmentItem> deletedAttachments = getDeletedAttachments();
        for (AttachmentItem attachment : deletedAttachments) {
            s3Service.deleteFile(attachment.getPathName());
        }
        attachmentRepository.deleteAll(deletedAttachments);
    }

    @Override
    public void deleteAttachmentByOwnerId(String ownerId) {
        List<AttachmentItem> attachments = attachmentRepository.findByOwnerIdAndIsDeleted(ownerId, false);
        for (AttachmentItem attachment : attachments) {
            attachment.setIsDeleted(true);
        }
    }

    @Override
    public void updateDeletedStatusById(List<AttachmentDto> attachments, Boolean isDeleted) {
        List<String> ids = attachments.stream().map(AttachmentDto::getId).toList();
        List<AttachmentItem> processFlowAttachments = attachmentRepository.findAllById(ids);
        for (AttachmentItem attachment : processFlowAttachments) {
            attachment.setIsDeleted(isDeleted);
        }
        attachmentRepository.saveAll(processFlowAttachments);
    }

    @Override
    public void updateDeletedByUrl(List<String> fileUrls, Boolean isDeleted) {
        List<AttachmentItem> attachments = attachmentRepository.findByFileUrlIn(fileUrls);
        for (AttachmentItem attachment : attachments) {
            attachment.setIsDeleted(isDeleted);
        }
        attachmentRepository.saveAll(attachments);
    }
}
