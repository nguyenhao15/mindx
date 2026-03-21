package com.example.demo01.domains.ProcessManagemnet.ProcessFlow.mapper;

import com.example.demo01.domains.ProcessManagemnet.ProcessFlow.dtos.ProcessFlow.ProcessFlowDto;
import com.example.demo01.domains.ProcessManagemnet.ProcessFlow.dtos.ProcessFlow.ProcessFlowRequest;
import com.example.demo01.domains.ProcessManagemnet.ProcessFlow.model.ProcessFlow;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProcessFlowMapper {

    ProcessFlow toProcessFlowFromDto(ProcessFlowRequest request);

    ProcessFlowDto toProcessFlowDto(ProcessFlow processFlow);

    ProcessFlow toProcessFlowFromDto(ProcessFlowDto processFlowDto);

    List<ProcessFlowDto> toProcessFlowDtoList(List<ProcessFlow> processFlows);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateProcessFlowFromDto(ProcessFlowRequest dto, @MappingTarget ProcessFlow processFlow);
}
