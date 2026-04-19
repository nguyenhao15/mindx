package com.example.demo01.repository.mongo.MiniCrmRepository.RentingRepository.Impl;

import com.example.demo01.configs.SecureUtil.SecurityRepoUtilImpl;
import com.example.demo01.domains.mongo.MiniCrm.Renting.model.RentingRecord;
import com.example.demo01.repository.mongo.MiniCrmRepository.RentingRepository.RentingCustomRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Query.query;

@RequiredArgsConstructor
public class RentingCustomRepoImpl implements RentingCustomRepo {

    private final MongoTemplate mongoTemplate;

    private final SecurityRepoUtilImpl securityRepoUtil;


    @Override
    public double getActiveRoomCount() {

        long count = mongoTemplate.count(
                securityRepoUtil.createSecureQuery(new Query(),"buId" ),
                RentingRecord.class
        );

        return (double) count;
    }

    @Override
    public List<RentingRecord> getActiveRentingByBuShortName(String buShortName) {
        Criteria criteria = new Criteria();

        criteria.andOperator(Criteria.where("bu_id").is(buShortName).and("active").is(true));

        Query query = query(criteria);
        securityRepoUtil.createSecureQuery(query, "buId");
        return mongoTemplate.find(query, RentingRecord.class);
    }
}
