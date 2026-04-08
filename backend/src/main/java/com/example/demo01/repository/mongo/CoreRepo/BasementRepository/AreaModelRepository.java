package com.example.demo01.repository.mongo.CoreRepo.BasementRepository;

import com.example.demo01.core.Basement.model.AreaModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AreaModelRepository extends MongoRepository<AreaModel, String> {
}
