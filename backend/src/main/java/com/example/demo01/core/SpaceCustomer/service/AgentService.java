package com.example.demo01.core.SpaceCustomer.service;

import com.example.demo01.core.SpaceCustomer.payload.Agent.DtoAgent;
import com.example.demo01.core.SpaceCustomer.payload.Agent.RequestAgent;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface AgentService {

    ResponseEntity<?> createNewAgent(RequestAgent requestAgent);

    DtoAgent getAgentById(String id);

    DtoAgent getAgentByIdNumber(String IdNumber);

    DtoAgent updateAgent(String id, DtoAgent dtoAgent);

    String deleteAgentById(String id);

    List<DtoAgent> searchAgents(String keyword);
}
