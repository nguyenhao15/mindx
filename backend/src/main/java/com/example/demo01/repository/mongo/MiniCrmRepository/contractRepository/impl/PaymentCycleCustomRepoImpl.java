package com.example.demo01.repository.mongo.MiniCrmRepository.contractRepository.impl;

import com.example.demo01.domains.MiniCrm.Contract.models.PaymentCycle;
import com.example.demo01.repository.mongo.MiniCrmRepository.contractRepository.PaymentCycleCustomRepo;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

@RequiredArgsConstructor
public class PaymentCycleCustomRepoImpl implements PaymentCycleCustomRepo {

    private final MongoTemplate mongoTemplate;

//    TODO: APPLY SECURITY CRITERIA
    @Override
    public Double getTotalDueAmountForDashBoard(LocalDate date) {
        LocalDate currentDate = LocalDate.now();
        LocalDate endOfMonth = currentDate.with(TemporalAdjusters.lastDayOfMonth());
        Criteria criteria = Criteria
                .where("cycleDueDate").lte(endOfMonth);

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(criteria),
                Aggregation.group()
                        .sum("remainAmount").as("totalAmount")
        );

        AggregationResults<Document> results = mongoTemplate.aggregate(
                aggregation,
                PaymentCycle.class,
                Document.class
        );

        Document doc = results.getUniqueMappedResult();

        return (doc != null) ? doc.getDouble("totalAmount") : 0.0;
    }


}
