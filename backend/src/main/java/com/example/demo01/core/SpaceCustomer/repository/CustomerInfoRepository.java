package com.example.demo01.core.SpaceCustomer.repository;

import com.example.demo01.core.SpaceCustomer.models.CustomerInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerInfoRepository extends MongoRepository<CustomerInfo, String> {
    CustomerInfo findByCustomerCodeAndIsDeletedFalse(String customerCode);

    Boolean existsByCustomerTaxCode(String customerTaxCode);

    Page<CustomerInfo> findByCustomerTitleContainingIgnoreCaseAndIsDeletedFalse(String customerTitle, Pageable pageable);

}
