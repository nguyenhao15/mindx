package com.example.demo01.repository.mongo.CoreRepo.BasementRepository;

import com.example.demo01.core.Basement.model.PriceRecordModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PriceRecordRepository extends MongoRepository<PriceRecordModel, String> {
    List<PriceRecordModel> findByBuShortNameIn(List<String> buShortName);

    PriceRecordModel findByBuShortName(String buShortName);
}
