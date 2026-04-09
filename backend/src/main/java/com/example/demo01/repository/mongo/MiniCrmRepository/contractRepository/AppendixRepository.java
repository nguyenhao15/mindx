package com.example.demo01.repository.mongo.MiniCrmRepository.contractRepository;

import com.example.demo01.domains.mongo.MiniCrm.Contract.models.Appendix;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppendixRepository extends MongoRepository<Appendix, String>, AppendixCustomRepo {

    List<Appendix> findByContractCode(String description);

    Appendix findByContractCodeAndAgreementNumber(String contractCode, String agreementNumber);

    List<Appendix> findByContractCodeAndActive(String contractCode, Boolean active);

    Appendix findByAppendixCode(String appendixCode);

    List<Appendix> findByCustomerId(String customerId);

    Page<Appendix> findByActive(Boolean active, Pageable pageable);

    Page<Appendix> findByActiveAndServiceId(Boolean active,String serviceId, Pageable pageable);

    Page<Appendix> findByBuId(String buId, Pageable pageable);
}
