package com.example.demo01.repository.mongo.MiniCrmRepository.paymentRepository;

import com.example.demo01.domains.mongo.MiniCrm.Payment.models.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository extends MongoRepository<Transaction, String>, TransactionCustomRepo {
    List<Transaction> getByPaymentOrderId(String paymentOrderId);

    List<Transaction> getByUsingId(String usingId);

    List<Transaction> getByInvoiceId(String invoiceId);

    Page<Transaction> getByPaymentDate(LocalDate paymentDate, Pageable page);

    Page<Transaction> getByBuId(String buId, Pageable pageable);

    List<Transaction> getByCustomerId(String customerId);

}
