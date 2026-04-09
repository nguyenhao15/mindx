package com.example.demo01.domains.mongo.ProcessManagement.ProcessFlow.dtos.ProcessFlow;


import com.example.demo01.core.Attachment.dto.AttachmentDto;
import com.example.demo01.domains.mongo.ProcessManagement.ProcessFlow.model.ProcessFlowTextContent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProcessFlowFullInfoDto {

    private ProcessFlowDto processFlowDto;

    private ProcessFlowTextContent processContent;

    private List<AttachmentDto> attachments;
}
