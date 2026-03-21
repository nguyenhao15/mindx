package com.example.demo01.core.Attachment.service;

import com.example.demo01.core.Attachment.dto.AttachmentDto;
import com.example.demo01.core.Attachment.model.AttachmentItem;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AttachmentService {

    List<AttachmentDto> addAttachment(List<MultipartFile> files, String ownerId, String pathName, Boolean isPublic);

    List<AttachmentDto> getAttachmentByOwnerId(String ownerId);

    AttachmentDto createAttachment(MultipartFile file, String ownerId, String folderName, Boolean isPublic);

    AttachmentItem getAttachmentItemById(String id);

    List<AttachmentItem> getDeletedAttachments();

    AttachmentDto getAttachmentById(String id);

    String getPreUrl(String id, Long expirationTimeInSeconds);

    void permanentlyDeleteAttachment();

    void deleteAttachmentByOwnerId(String ownerId);

    void updateDeletedStatusById(List<AttachmentDto> attachments, Boolean isDeleted);

    void updateDeletedByUrl(List<String> fileUrls, Boolean isDeleted);
}
