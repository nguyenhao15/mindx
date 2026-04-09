package com.example.demo01.domains.mongo.ProcessManagement.ProcessTag.mapper;


import com.example.demo01.domains.mongo.ProcessManagement.ProcessTag.dtos.processValue.ProcessTagValueDto;
import com.example.demo01.domains.mongo.ProcessManagement.ProcessTag.dtos.processValue.ProcessTagValueNestFieldDto;
import com.example.demo01.domains.mongo.ProcessManagement.ProcessTag.dtos.processValue.ProcessTagValueRequest;
import com.example.demo01.domains.mongo.ProcessManagement.ProcessTag.models.ProcessTagValue;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProcessTagValueMapper {

    ProcessTagValue toEntity(ProcessTagValueRequest dto);

    ProcessTagValueDto toDto(ProcessTagValue entity);

    List<ProcessTagValueDto> toDtoList(List<ProcessTagValue> entities);

    List<ProcessTagValueNestFieldDto> toTagValueNestFieldDtoList(List<ProcessTagValue> dtos);

    List<ProcessTagValueNestFieldDto> toNestFieldDtoList(List<ProcessTagValue> entities);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(ProcessTagValueRequest dto, @MappingTarget ProcessTagValue entity);

}
