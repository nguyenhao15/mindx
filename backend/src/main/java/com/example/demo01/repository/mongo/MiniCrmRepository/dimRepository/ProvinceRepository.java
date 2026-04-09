package com.example.demo01.repository.mongo.MiniCrmRepository.dimRepository;

import com.example.demo01.domains.mongo.MiniCrm.Dimmesion.model.provinceModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProvinceRepository extends MongoRepository<provinceModel, String> {
}
