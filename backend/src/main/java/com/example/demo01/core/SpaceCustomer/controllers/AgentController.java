package com.example.demo01.core.SpaceCustomer.controllers;

import com.example.demo01.core.SpaceCustomer.payload.Agent.DtoAgent;
import com.example.demo01.core.SpaceCustomer.payload.Agent.RequestAgent;
import com.example.demo01.core.SpaceCustomer.payload.CustomerInfo.CustomerInfoDTO;
import com.example.demo01.core.SpaceCustomer.service.AgentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/space/customer/agent")
public class AgentController {

    private final AgentService agentService;

    @PostMapping
    public ResponseEntity<?> createNewAgent(@Valid @RequestBody RequestAgent requestAgent) {
        return agentService.createNewAgent(requestAgent);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DtoAgent> getAgentById(@PathVariable String id) {
        DtoAgent agentPerson = agentService.getAgentById(id);
        return ResponseEntity.ok(agentPerson);
    }

    @GetMapping("/{idNumber}/id-number")
    public ResponseEntity<DtoAgent> getAgentByIdNumber(@PathVariable String idNumber) {
        DtoAgent agentPerson = agentService.getAgentByIdNumber(idNumber);
        return new ResponseEntity<>(agentPerson, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<DtoAgent> updateAgentById(@PathVariable String id,
                                                    @RequestBody DtoAgent dtoAgent) {
        DtoAgent agent = agentService.updateAgent(id, dtoAgent);
        return new ResponseEntity<>(agent, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAgentById(@PathVariable String id) {
        String deletedMessage = agentService.deleteAgentById(id);
        return new ResponseEntity<>(deletedMessage, HttpStatus.OK);
    }

    @GetMapping("/{search}/keyword")
    public ResponseEntity<List<DtoAgent>> searchAgents(@PathVariable String search) {
        List<DtoAgent> agents = agentService.searchAgents(search);
        return new ResponseEntity<>(agents, HttpStatus.OK);
    }

    @SchemaMapping(typeName = "Customer", field = "representativePerson")
    public DtoAgent getRepresentativePerson(CustomerInfoDTO customer) {
        return agentService.getAgentById(customer.getRepresentativeId());
    }
}
