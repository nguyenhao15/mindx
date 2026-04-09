package com.example.demo01.repository.mongo.MiniCrmRepository.dimRepository;

import com.example.demo01.domains.mongo.MiniCrm.Dimmesion.model.CurrencyDB;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CurrencyDbRepository extends MongoRepository<CurrencyDB, String> {
    List<CurrencyDB> findByActive(Boolean active);
}
