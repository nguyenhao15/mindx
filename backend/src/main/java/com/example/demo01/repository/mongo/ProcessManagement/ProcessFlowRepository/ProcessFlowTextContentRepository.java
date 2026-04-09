package com.example.demo01.repository.mongo.ProcessManagement.ProcessFlowRepository;

import com.example.demo01.domains.mongo.ProcessManagement.ProcessFlow.model.ProcessFlowTextContent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessFlowTextContentRepository extends MongoRepository<ProcessFlowTextContent, String> {
    ProcessFlowTextContent findByProcessFlowId(String processFlowId);
}
