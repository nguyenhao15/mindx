package com.example.demo01.domains.ProcessManagemnet.ProcessFlow.dtos.ProcessFlow;


import com.example.demo01.domains.ProcessManagemnet.ProcessFlow.dtos.ProcessFlowTextContent.ProcessFlowContentRequest;
import com.example.demo01.domains.ProcessManagemnet.ProcessFlow.model.ProcessFlowStatus;
import com.example.demo01.domains.ProcessManagemnet.ProcessTag.dtos.processTag.ProcessTagNestFieldDto;
import com.example.demo01.domains.ProcessManagemnet.ProcessTag.dtos.processValue.ProcessTagValueNestFieldDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProcessFlowRequest {

    @NotEmpty
    private String title;

    @NotBlank(message = "Description cannot be blank")
    private String description;

    private Boolean active = false;

    private ProcessFlowStatus processStatus;

    private ProcessFlowAccessRule accessRule = new ProcessFlowAccessRule();

    private ProcessFlowContentRequest processContent;

    @NotEmpty(message = "Process tags cannot be empty")
    private List<ProcessTagNestFieldDto> tagItems;

    @NotEmpty(message = "Tag values cannot be empty")
    private List<ProcessTagValueNestFieldDto> tagIdValues;

}
