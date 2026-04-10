package com.example.demo01.domains.mongo.ProcessManagement.ProcessFlow.model;

import com.example.demo01.utils.BaseEntity.Mongo.BaseAuditModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Document(collection = "processFlowTextContents")
public class ProcessFlowTextContent extends BaseAuditModel {

    private String id;
    private String content;
    private String processFlowId;
}
