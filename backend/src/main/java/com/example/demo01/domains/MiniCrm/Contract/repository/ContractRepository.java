package com.example.demo01.domains.MiniCrm.Contract.repository;

import com.example.demo01.configs.SecureRepoConfig.BaseRepository;
import com.example.demo01.domains.MiniCrm.Contract.models.Contract;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContractRepository extends BaseRepository<Contract, String> {

    Optional<Contract> findByContractCode(String contractCode);

    Long deleteByContractCode(String contractCode);

    Page<Contract> getByCustomerId(String CustomerId, Pageable pageable);

    Page<Contract> getByBuId(String BuId, Pageable pageable);

    default Page<Contract> findByContractStatus(String status, Pageable pageable) {
        Query query = new Query();
        query.addCriteria(Criteria.where("contractStatus").is(status));

        return findAllSecure(query, pageable);
    }


}
