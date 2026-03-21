package com.example.demo01.core.SpaceCustomer.mapper;

import com.example.demo01.core.SpaceCustomer.models.AgentPerson;
import com.example.demo01.core.SpaceCustomer.payload.Agent.DtoAgent;
import com.example.demo01.core.SpaceCustomer.payload.Agent.RequestAgent;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;


import java.util.List;

@Mapper(componentModel = "spring")
public interface AgentMapper {

    AgentPerson toEntity(RequestAgent requestDto);
    DtoAgent toDto(AgentPerson agentPerson);
    List<DtoAgent> toDto(List<AgentPerson> customerContactPeople);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateContactPerson(DtoAgent dtoContactPerson, @MappingTarget AgentPerson agentPerson);
}
