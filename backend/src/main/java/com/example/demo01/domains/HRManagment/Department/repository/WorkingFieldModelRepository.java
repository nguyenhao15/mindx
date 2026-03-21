package com.example.demo01.domains.HRManagment.Department.repository;

import com.example.demo01.domains.HRManagment.Department.model.WorkingField;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface WorkingFieldModelRepository extends MongoRepository<WorkingField, String> {

    List<WorkingField> findByActive(Boolean isActive);

    WorkingField getWorkingFieldByFieldCode(String fieldCode);

    List<WorkingField> findByFieldCodeIn(List<String> fieldCodes);

}
