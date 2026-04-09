package com.example.demo01.repository.mongo.MiniCrmRepository.RentingRepository;

import com.example.demo01.domains.mongo.MiniCrm.Renting.model.RentingRecord;

import java.util.List;

public interface RentingCustomRepo {

    double getActiveRoomCount();

    List<RentingRecord> getActiveRentingByBuShortName(String buShortName);

}
