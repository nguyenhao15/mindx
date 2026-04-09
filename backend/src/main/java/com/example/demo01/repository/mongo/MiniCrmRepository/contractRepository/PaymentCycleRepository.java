package com.example.demo01.repository.mongo.MiniCrmRepository.contractRepository;

import com.example.demo01.configs.Mongo.SecureRepoConfig.BaseRepository;
import com.example.demo01.domains.mongo.MiniCrm.Contract.models.PaymentCycle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentCycleRepository extends BaseRepository<PaymentCycle, String>, PaymentCycleCustomRepo {
    List<PaymentCycle> getByContractId(String contractId);

    default Page<PaymentCycle> getByDoneCheck(Boolean done, Pageable pageable) {
        Query query = new Query();
        query.addCriteria(Criteria.where("active").is(done));
        return findAllSecure(query, pageable);
    };

    List<PaymentCycle> getByAppendixId(String appendixId);

    List<PaymentCycle> getByAppendixIdAndActive(String appendixId, Boolean active);

    default Double calculateTotalRemainingAmount(String contractCode) {
        Query query = new Query(Criteria.where("appendixId").is(contractCode));
        List<PaymentCycle> paymentCycleList = findAllSecure(query);
        return paymentCycleList.stream()
                .mapToDouble(p -> {
                    return (double) p.getRemainAmount();
                }).
                sum();
    }

    Page<PaymentCycle> getByBuId(String buId, Pageable pageable);
}
