package com.example.demo01.repository.mongo.CoreRepo.BasementRepository;

import com.example.demo01.core.Basement.model.RoomModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface RoomModelRepository extends MongoRepository<RoomModel, String> {

    Page<RoomModel> getByBuShortName(String buShortName, Pageable pageable);

    List<RoomModel> getByBuShortName(String buShortName);

    List<RoomModel> findByBuShortNameIn(List<String> shortNames);

}
