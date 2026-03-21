package com.example.demo01.domains.MiniCrm.Renting.repository;

import com.example.demo01.domains.MiniCrm.Renting.model.RentingRecord;

import java.util.List;

public interface RentingCustomRepo {

    double getActiveRoomCount();

    List<RentingRecord> getActiveRentingByBuShortName(String buShortName);

}
