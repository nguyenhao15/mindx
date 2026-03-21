package com.example.demo01.core.Basement.repository;

import com.example.demo01.core.Basement.model.AreaModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AreaModelRepository extends MongoRepository<AreaModel, String> {
}
