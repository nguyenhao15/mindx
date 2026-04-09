package com.example.demo01.domains.mongo.ProcessManagement.ProcessFlow.dtos.ProcessFlow;


import com.example.demo01.domains.mongo.ProcessManagement.ProcessFlow.model.ProcessFlowStatus;
import com.example.demo01.domains.mongo.ProcessManagement.ProcessTag.dtos.processTag.ProcessTagNestFieldDto;
import com.example.demo01.domains.mongo.ProcessManagement.ProcessTag.dtos.processValue.ProcessTagValueNestFieldDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProcessFlowDto {

    private String id;

    private String title;

    private String description;

    private Boolean active;

    private Boolean isOfficial;

    private ProcessFlowStatus processStatus;

    private ProcessFlowAccessRule accessRule;

    private List<ProcessTagNestFieldDto> tagItems;

    private List<ProcessTagValueNestFieldDto> tagIdValues;

    private String createdBy;
    private String lastModifiedBy;
    private Instant createdDate;
    private Instant lastModifiedDate;
    private Long version;
}
