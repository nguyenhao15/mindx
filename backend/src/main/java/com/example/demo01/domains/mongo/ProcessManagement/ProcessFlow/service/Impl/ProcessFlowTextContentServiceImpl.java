package com.example.demo01.domains.mongo.ProcessManagement.ProcessFlow.service.Impl;

import com.example.demo01.configs.Constants.FolderConstants;
import com.example.demo01.core.Attachment.dto.AttachmentDto;
import com.example.demo01.core.Attachment.dto.AttachmentWithUrl;
import com.example.demo01.core.Attachment.service.AttachmentService;
import com.example.demo01.domains.mongo.ProcessManagement.ProcessFlow.dtos.ProcessFlowTextContent.ProcessFlowContentRequest;
import com.example.demo01.domains.mongo.ProcessManagement.ProcessFlow.model.ProcessFlowTextContent;
import com.example.demo01.repository.mongo.ProcessManagement.ProcessFlowRepository.ProcessFlowTextContentRepository;
import com.example.demo01.domains.mongo.ProcessManagement.ProcessFlow.service.ProcessFlowTextContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProcessFlowTextContentServiceImpl implements ProcessFlowTextContentService {

    private final ProcessFlowTextContentRepository processFlowTextContentRepository;

    private final AttachmentService attachmentService;

    @Override
    public ProcessFlowTextContent createNewContent(ProcessFlowContentRequest processFlowContentRequest) {
        ProcessFlowTextContent processFlowTextContent = new ProcessFlowTextContent();
        processFlowTextContent.setProcessFlowId(processFlowContentRequest.getProcessFlowId());
        processFlowTextContent.setContent(processFlowContentRequest.getContent());
        List<AttachmentWithUrl> attachments = processFlowContentRequest.getAttachments();
        if (attachments != null) {
            List<String> deletedAttachments = attachments.stream().filter(
                    ite -> ite.getIsDeleted() == true )
                    .map(AttachmentWithUrl::getFileUrl).toList();
            if (!attachments.isEmpty()) {
                attachmentService.updateDeletedByUrl(deletedAttachments, true);
            }
        }

        return processFlowTextContentRepository.save(processFlowTextContent);
    }

    @Override
    public ProcessFlowTextContent getProcessFlowTextContent(String processFlowId) {
        ProcessFlowTextContent existingContent = processFlowTextContentRepository.findByProcessFlowId(processFlowId);
        if (existingContent == null) {
            ProcessFlowTextContent processFlowTextContent =  new ProcessFlowTextContent();
            processFlowTextContent.setProcessFlowId(processFlowId);
            processFlowTextContent.setContent("Description is empty");
            return processFlowTextContent;
        }
        return existingContent;
    }

    @Override
    public AttachmentDto uploadFileInTextEditor(MultipartFile fileData) {
        String processFolder = FolderConstants.PROCESS;
        String folderName = processFolder + "/IN-DOCUMENTS";
        return attachmentService.createAttachment(fileData,"",folderName,true);
    }

    @Override
    public ProcessFlowTextContent updateProcessFlowTextContent(ProcessFlowContentRequest processFlowContentRequest) {
        ProcessFlowTextContent existingContent = processFlowTextContentRepository.findByProcessFlowId(processFlowContentRequest.getProcessFlowId());
        List<AttachmentWithUrl> attachments = processFlowContentRequest.getAttachments();

        if (existingContent != null) {
            existingContent.setContent(processFlowContentRequest.getContent());
            if (attachments != null) {
                List<AttachmentWithUrl> deletedAttachments = attachments.stream().filter(
                        ite -> ite.getIsDeleted() == true ).toList();
                if (!deletedAttachments.isEmpty()) {
                    List<String> deletedAttachmentUrls = deletedAttachments.stream().map(AttachmentWithUrl::getFileUrl).toList();
                    attachmentService.updateDeletedByUrl(deletedAttachmentUrls, true);
                }
            }
            return processFlowTextContentRepository.save(existingContent);
        }

        return null;
    }
}
