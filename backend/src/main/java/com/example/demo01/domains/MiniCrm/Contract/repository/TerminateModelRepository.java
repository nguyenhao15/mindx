package com.example.demo01.domains.MiniCrm.Contract.repository;

import com.example.demo01.domains.MiniCrm.Contract.models.TerminateModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TerminateModelRepository extends MongoRepository<TerminateModel,String> {
    TerminateModel findByAppendixCode(String appendixCode);
}

