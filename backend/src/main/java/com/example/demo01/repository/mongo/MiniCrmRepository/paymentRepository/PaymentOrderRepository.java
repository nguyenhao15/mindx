package com.example.demo01.repository.mongo.MiniCrmRepository.paymentRepository;

import com.example.demo01.domains.MiniCrm.Payment.models.PaymentOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentOrderRepository extends MongoRepository<PaymentOrder, String> {
    Page<PaymentOrder> getByIsConfirmed(String confirmStatus, Pageable pageable);
}
