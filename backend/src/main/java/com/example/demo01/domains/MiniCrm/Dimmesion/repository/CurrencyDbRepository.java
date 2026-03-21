package com.example.demo01.domains.MiniCrm.Dimmesion.repository;

import com.example.demo01.domains.MiniCrm.Dimmesion.model.CurrencyDB;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CurrencyDbRepository extends MongoRepository<CurrencyDB, String> {
    List<CurrencyDB> findByActive(Boolean active);
}
