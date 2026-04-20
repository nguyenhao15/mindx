package com.example.demo01.repository.mongo.HRManagement.StaffProfileRepository;

import com.example.demo01.domains.mongo.HRManagment.HumanResource.model.StaffProfileModels;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StaffProfileRepository extends MongoRepository<StaffProfileModels, String> {

    List<StaffProfileModels> getByStaffId(String staffId);

    List<StaffProfileModels> getByStaffIdAndActive(String staffId, Boolean active);

    StaffProfileModels findByStaffIdAndIsDefault(String staffId, Boolean isDefault);
}
