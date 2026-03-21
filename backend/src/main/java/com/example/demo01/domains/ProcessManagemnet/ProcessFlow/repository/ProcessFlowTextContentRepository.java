package com.example.demo01.domains.ProcessManagemnet.ProcessFlow.repository;

import com.example.demo01.domains.ProcessManagemnet.ProcessFlow.model.ProcessFlowTextContent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessFlowTextContentRepository extends MongoRepository<ProcessFlowTextContent, String> {
    ProcessFlowTextContent findByProcessFlowId(String processFlowId);
}
