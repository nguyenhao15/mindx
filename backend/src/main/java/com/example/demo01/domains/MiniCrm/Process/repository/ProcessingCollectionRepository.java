package com.example.demo01.domains.MiniCrm.Process.repository;

import com.example.demo01.domains.MiniCrm.Process.model.ProcessingCollection;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessingCollectionRepository extends MongoRepository<ProcessingCollection,String>, ProcessingCollectionCustomRepo {
    ProcessingCollection getByProcessCode(String processCode);
}
