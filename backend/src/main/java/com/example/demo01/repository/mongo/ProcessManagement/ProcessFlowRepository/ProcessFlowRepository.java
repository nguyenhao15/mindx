package com.example.demo01.repository.mongo.ProcessManagement.ProcessFlowRepository;

import com.example.demo01.domains.ProcessManagement.ProcessFlow.model.ProcessFlow;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessFlowRepository extends MongoRepository<ProcessFlow, String> {
}
