package com.example.demo01.core.SpaceCustomer.service.impl;

import com.example.demo01.core.SpaceCustomer.mapper.AgentMapper;
import com.example.demo01.core.SpaceCustomer.models.AgentPerson;
import com.example.demo01.core.SpaceCustomer.payload.Agent.DtoAgent;
import com.example.demo01.core.SpaceCustomer.payload.Agent.RequestAgent;
import com.example.demo01.core.SpaceCustomer.repository.AgentRepository;
import com.example.demo01.core.SpaceCustomer.service.AgentService;
import com.example.demo01.core.Exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AgentServiceImpl implements AgentService {

    private final AgentRepository agentRepository;

    private final AgentMapper agentMapper;

    private final MongoTemplate mongoTemplate;

    @Override
    public ResponseEntity<?> createNewAgent(RequestAgent requestAgent) {
        try {
            AgentPerson agent = agentRepository.save(agentMapper.toEntity(requestAgent));
            DtoAgent dtoAgent = agentMapper.toDto(agent);
            return new ResponseEntity<>(dtoAgent, HttpStatus.CREATED);
        } catch (DuplicateKeyException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Agent Person with the same idNumber already exists.");
        }
    }

    @Override
    public DtoAgent getAgentById(String id) {
        AgentPerson agentPerson = agentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AgentPerson", "_id", id));
        return agentMapper.toDto(agentPerson);
    }

    @Override
    public DtoAgent getAgentByIdNumber(String IdNumber) {
        AgentPerson agentPerson = agentRepository.findByIdNumber(IdNumber);
        if (agentPerson == null) {
            throw new ResourceNotFoundException("AgentPerson", "idNumber", IdNumber);
        }
        return agentMapper.toDto(agentPerson);
    }

    @Override
    public DtoAgent updateAgent(String id, DtoAgent dtoAgent) {
        AgentPerson agentPerson = agentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AgentPerson", "_id", id));

        agentMapper.updateContactPerson(dtoAgent, agentPerson);

        AgentPerson updatedAgentPerson = agentRepository.save(agentPerson);

        return agentMapper.toDto(updatedAgentPerson);
    }

    @Override
    public String deleteAgentById(String id) {
        agentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AgentPerson", "_id", id));

        agentRepository.deleteById(id);

        return "Deleted agent with id: " + id + " successfully!";

    }

    @Override
    public List<DtoAgent> searchAgents(String keyword) {
        Query query = new Query();

        if (keyword != null && !keyword.isEmpty()) {
            Criteria searchCriteria = new Criteria().orOperator(
                    Criteria.where("fullName").regex(keyword, "i"),
                    Criteria.where("idNumber").regex(keyword, "i")
            );

            query.addCriteria(searchCriteria);
        }

        return mongoTemplate.find(query, AgentPerson.class)
                .stream()
                .map(agentMapper::toDto)
                .toList();
    }
}
