package com.example.demo01.repository.mongo.MiniCrmRepository.profitAndLostRepository;

import com.example.demo01.domains.mongo.MiniCrm.ProfitAndLost.model.ProfitAndLost;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProfitAndLostRepository extends MongoRepository<ProfitAndLost, String> {
    List<ProfitAndLost> findByTrackIdAndRemainAmountGreaterThan(String trackId, double amount);

    @Query(value = "{ 'appendixCode' : ?0 }", delete = true)
    Long deleteByAppendixCode(String appendixCode);

    List<ProfitAndLost> findByTrackId(String trackId);

    List<ProfitAndLost> findByContractIdAndAllocationDateAfter(String contractId, LocalDate allocationDate);

}
