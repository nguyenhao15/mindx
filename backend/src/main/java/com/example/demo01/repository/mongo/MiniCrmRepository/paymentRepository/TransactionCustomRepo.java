package com.example.demo01.repository.mongo.MiniCrmRepository.paymentRepository;

import com.example.demo01.domains.MiniCrm.Payment.models.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface TransactionCustomRepo {
    Double getRevenueBetween(LocalDate startDate, LocalDate endDate);

    Page<Transaction> getPaymentDetail(Pageable pageable);
}
