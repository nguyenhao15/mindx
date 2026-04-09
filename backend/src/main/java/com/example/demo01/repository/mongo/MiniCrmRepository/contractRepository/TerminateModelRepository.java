package com.example.demo01.repository.mongo.MiniCrmRepository.contractRepository;

import com.example.demo01.domains.mongo.MiniCrm.Contract.models.TerminateModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TerminateModelRepository extends MongoRepository<TerminateModel,String> {
    TerminateModel findByAppendixCode(String appendixCode);
}

