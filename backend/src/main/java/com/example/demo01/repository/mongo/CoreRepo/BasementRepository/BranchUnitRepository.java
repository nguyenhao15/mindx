package com.example.demo01.repository.mongo.CoreRepo.BasementRepository;

import com.example.demo01.core.Basement.model.BranchUnit;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface BranchUnitRepository extends MongoRepository<BranchUnit, String> {

    BranchUnit findByBuId(String branchUnitId);

    List<BranchUnit> findAllByBuIdIn(Collection<String> buShortNames);

    List<BranchUnit> findByActive(Boolean active);
}
