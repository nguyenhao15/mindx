package com.example.demo01.repository.mongo.MiniCrmRepository.invoiceRepository.Impl;

import com.example.demo01.domains.MiniCrm.Invoice.model.Invoice;
import com.example.demo01.repository.mongo.MiniCrmRepository.invoiceRepository.InvoiceCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

@RequiredArgsConstructor
public class InvoiceCustomRepositoryImpl implements InvoiceCustomRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public List<Invoice> getActiveInvoicesByCustomerId(String customerId) {
        Criteria criteria =  Criteria.where("customerId").is(customerId).and("active").is(true);

        Query query = new Query(criteria);

        return mongoTemplate.find(query, Invoice.class);
    }



}
