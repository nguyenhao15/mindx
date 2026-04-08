package com.example.demo01.repository.mongo.MiniCrmRepository.dimRepository;

import com.example.demo01.domains.MiniCrm.Dimmesion.model.ProductModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends MongoRepository<ProductModel, String> {
    Optional<ProductModel> findByServiceShortName(String serviceShortName);
}
