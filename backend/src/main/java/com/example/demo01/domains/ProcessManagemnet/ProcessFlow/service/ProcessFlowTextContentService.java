package com.example.demo01.domains.ProcessManagemnet.ProcessFlow.service;

import com.example.demo01.core.Attachment.dto.AttachmentDto;
import com.example.demo01.domains.ProcessManagemnet.ProcessFlow.dtos.ProcessFlowTextContent.ProcessFlowContentRequest;
import com.example.demo01.domains.ProcessManagemnet.ProcessFlow.model.ProcessFlowTextContent;
import org.springframework.web.multipart.MultipartFile;

public interface ProcessFlowTextContentService {

    ProcessFlowTextContent createNewContent(ProcessFlowContentRequest processFlowContentRequest);

    ProcessFlowTextContent getProcessFlowTextContent(String processFlowId);

    AttachmentDto uploadFileInTextEditor(MultipartFile fileData);

    ProcessFlowTextContent updateProcessFlowTextContent(ProcessFlowContentRequest processFlowContentRequest);

}
