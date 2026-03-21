package com.example.demo01.domains.MiniCrm.Renting.repository;

import com.example.demo01.configs.SecureRepoConfig.BaseRepository;
import com.example.demo01.domains.MiniCrm.Renting.model.RentingRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface RentingContractRepository extends BaseRepository<RentingRecord, String>, RentingCustomRepo {
    List<RentingRecord> getByContractId(String id);

    List<RentingRecord> findAllByContractIdIn(Collection<String> contractCodes);

    Page<RentingRecord> getByBuId(String buShortName, Pageable pageable);

    List<RentingRecord> getByCustomerId(String customerId);

    void deleteByContractId(String contractId);

    RentingRecord getRoomByRoomShortNameAndActive(String roomShortName, Boolean active);

    default Page<RentingRecord> getByStatus(String status, Pageable pageable) {
        Query query = new Query();
        query.addCriteria(Criteria.where("Status").is(status));
        return findAllSecure(query, pageable);
    };
}
