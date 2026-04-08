package com.example.demo01.repository.mongo.HRManagement.departmentRepository;

import com.example.demo01.domains.HRManagment.Department.model.DepartmentModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentModelRepository extends MongoRepository<DepartmentModel, String> {

    List<DepartmentModel> findByActive(Boolean active);

    DepartmentModel findByDepartmentCode(String departmentCode);

    List<DepartmentModel> findByDepartmentCodeIn(List<String> departmentCode);
}

