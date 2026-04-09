package com.example.demo01.repository.mongo.ProcessManagement.ProcessTagRepository;

import com.example.demo01.domains.mongo.ProcessManagement.ProcessTag.models.ProcessTagValue;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProcessTagValueRepository extends MongoRepository<ProcessTagValue, String> {

    long countByIdIn(List<String> ids);

    List<ProcessTagValue> findByActive(boolean active);

}
