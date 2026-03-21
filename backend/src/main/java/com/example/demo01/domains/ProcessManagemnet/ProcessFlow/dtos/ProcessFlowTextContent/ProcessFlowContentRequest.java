package com.example.demo01.domains.ProcessManagemnet.ProcessFlow.dtos.ProcessFlowTextContent;

import com.example.demo01.core.Attachment.dto.AttachmentWithUrl;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProcessFlowContentRequest {

    private String content;
    private String processFlowId;

//    This is in line attachments
    private List<AttachmentWithUrl> attachments;

}

