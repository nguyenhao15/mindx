package com.example.demo01.domains.mongo.ProcessManagement.ProcessFlow.model;


import com.example.demo01.domains.mongo.ProcessManagement.ProcessFlow.dtos.ProcessFlow.ProcessFlowAccessRule;
import com.example.demo01.domains.mongo.ProcessManagement.ProcessTag.dtos.processTag.ProcessTagNestFieldDto;
import com.example.demo01.domains.mongo.ProcessManagement.ProcessTag.dtos.processValue.ProcessTagValueNestFieldDto;
import com.example.demo01.utils.BaseModels.BaseAuditModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Document(collection = "processFlows")
public class ProcessFlow extends BaseAuditModel {

    private String id;

    @Indexed
    private String title;

    @Indexed
    private String description;

    private Boolean active;

    private Boolean isOfficial;

    private ProcessFlowAccessRule accessRule;

    private ProcessFlowStatus processStatus;

    @Indexed
    private List<ProcessTagNestFieldDto> tagItems;

    @Indexed
    private List<ProcessTagValueNestFieldDto> tagIdValues;

}
