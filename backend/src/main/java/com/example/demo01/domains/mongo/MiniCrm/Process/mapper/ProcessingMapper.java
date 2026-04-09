package com.example.demo01.domains.mongo.MiniCrm.Process.mapper;

import com.example.demo01.domains.mongo.MiniCrm.Process.dtos.ProcessingInfoDto;
import com.example.demo01.domains.mongo.MiniCrm.Process.dtos.ProcessingRequest;
import com.example.demo01.domains.mongo.MiniCrm.Process.model.ProcessingCollection;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;


import java.util.List;

@Mapper(componentModel = "spring")
public interface ProcessingMapper {

    ProcessingCollection toEntity(ProcessingRequest processingRequest);

    ProcessingInfoDto toDto(ProcessingCollection processingCollection);

    List<ProcessingCollection> toEntity(List<ProcessingInfoDto> processingInfoDtoList);

    List<ProcessingInfoDto> toDtoList(List<ProcessingCollection> processingCollectionList);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(ProcessingRequest processingRequest,@MappingTarget ProcessingCollection entity);
}
