package com.example.demo01.repository.mongo.MiniCrmRepository.paymentRepository.repoImpl;

import com.example.demo01.configs.SecureUtil.SecurityRepoUtilImpl;
import com.example.demo01.domains.mongo.MiniCrm.Payment.models.Transaction;
import com.example.demo01.repository.mongo.MiniCrmRepository.paymentRepository.TransactionCustomRepo;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
public class TransactionCustomRepoImpl implements TransactionCustomRepo {

    private final MongoTemplate mongoTemplate;
    private final SecurityRepoUtilImpl securityRepoUtil;

    @Override
    public Double getRevenueBetween(LocalDate startDate, LocalDate endDate) {
        Criteria criteria = Criteria.where("paymentDate").gte(startDate).lte(endDate);
        Criteria securityCriteria = securityRepoUtil.getSecurityCriteriaByBu("buId");

        if (securityCriteria != null && !securityCriteria.getCriteriaObject().isEmpty()) {
            criteria = criteria.andOperator(securityCriteria);
        }

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(criteria),
                Aggregation.group()
                        .sum("localAmount").as("totalAmount")
        );

        AggregationResults<Document> results = mongoTemplate.aggregate(
                aggregation,
                Transaction.class,
                Document.class
        );
        Document doc = results.getUniqueMappedResult();
        if (doc == null) {
            return 0.0;
        }

        Number totalAmount =  doc.get("totalAmount", Number.class);
        return totalAmount != null ? totalAmount.doubleValue() : 0.0;
    }

    @Override
    public Page<Transaction> getPaymentDetail(Pageable pageable) {
        Query query = new Query();
        securityRepoUtil.createSecureQuery(query,"buId" );
        List<Transaction> transactions = mongoTemplate.find(query.with(pageable), Transaction.class);
        long count = mongoTemplate.count(query, Transaction.class);
        return new PageImpl<>(transactions, pageable, count);
    }
}
