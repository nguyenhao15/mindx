package com.example.demo01.domains.MiniCrm.Dimmesion.repository;

import com.example.demo01.domains.MiniCrm.Dimmesion.model.provinceModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProvinceRepository extends MongoRepository<provinceModel, String> {
}
