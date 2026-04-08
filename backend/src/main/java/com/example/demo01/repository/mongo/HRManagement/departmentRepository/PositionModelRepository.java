package com.example.demo01.repository.mongo.HRManagement.departmentRepository;

import com.example.demo01.domains.HRManagment.Department.model.PositionModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PositionModelRepository extends MongoRepository<PositionModel, String> {
    List<PositionModel> findByDepartmentCode(String departmentId);

    List<PositionModel> findByIdIn(List<String> positionIds);

    List<PositionModel> findByActive(Boolean isActive);
}
