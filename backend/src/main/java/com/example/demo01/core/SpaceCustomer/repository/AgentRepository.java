package com.example.demo01.core.SpaceCustomer.repository;

import com.example.demo01.core.SpaceCustomer.models.AgentPerson;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgentRepository extends MongoRepository<AgentPerson, String> {

    AgentPerson findByIdNumber(String idNumber);

    List<AgentPerson> findByFullName(String fullName);

}
