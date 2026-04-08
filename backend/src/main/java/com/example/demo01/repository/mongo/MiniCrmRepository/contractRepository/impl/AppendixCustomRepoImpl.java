package com.example.demo01.repository.mongo.MiniCrmRepository.contractRepository.impl;

import com.example.demo01.domains.MiniCrm.Contract.dtos.appendix.AppendixInfoDto;
import com.example.demo01.domains.MiniCrm.Contract.mappers.AppendixMapper;
import com.example.demo01.domains.MiniCrm.Contract.models.Appendix;
import com.example.demo01.repository.mongo.MiniCrmRepository.contractRepository.AppendixCustomRepo;
import com.example.demo01.utils.AppUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

@RequiredArgsConstructor
public class AppendixCustomRepoImpl implements AppendixCustomRepo {

    private final MongoTemplate mongoTemplate;

    private final AppendixMapper appendixMapper;

    private final AppUtil appUtil;

    @Override
    public List<AppendixInfoDto> getActiveAppendixByCustomerIdAndBuId(String customerId, String buId) {
        List<Criteria> criteriaList = List.of(
                Criteria.where("customerId").is(customerId),
                Criteria.where("buId").is(buId),
                Criteria.where("active").is(true)
        );

        Query query = appUtil.applyFilter( null, criteriaList);

        return mongoTemplate.find(query, Appendix.class)
                .stream().map(appendixMapper::toDto)
                .toList();
    }

    @Override
    public List<Appendix> getActiveAppendixByBuShortNameAndServiceIDAndCustomerId(String buShortName, String serviceId, String customerId) {
        List<Criteria> criteriaList = List.of(
                Criteria.where("customerId").is(customerId),
                Criteria.where("bu_id").is(buShortName),
                Criteria.where("active").is(true),
                Criteria.where("serviceId").is(serviceId)
        );

        Query query = appUtil.applyFilter(null, criteriaList);

        return mongoTemplate.find(query, Appendix.class);
    }
}
