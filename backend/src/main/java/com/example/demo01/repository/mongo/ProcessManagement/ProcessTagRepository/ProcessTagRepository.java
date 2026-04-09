package com.example.demo01.repository.mongo.ProcessManagement.ProcessTagRepository;


import com.example.demo01.domains.mongo.ProcessManagement.ProcessTag.models.ProcessTag;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProcessTagRepository extends MongoRepository<ProcessTag, String> {
    long countByIdIn(List<String> ids);

    List<ProcessTag> findByActive(Boolean active);
}
