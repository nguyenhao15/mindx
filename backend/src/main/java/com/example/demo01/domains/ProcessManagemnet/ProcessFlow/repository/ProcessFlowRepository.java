package com.example.demo01.domains.ProcessManagemnet.ProcessFlow.repository;

import com.example.demo01.domains.ProcessManagemnet.ProcessFlow.model.ProcessFlow;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessFlowRepository extends MongoRepository<ProcessFlow, String> {
}
