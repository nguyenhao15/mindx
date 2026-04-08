package com.example.demo01.domains.ProcessManagement.ProcessTag.mapper;


import com.example.demo01.domains.ProcessManagement.ProcessTag.dtos.processTag.ProcessTagDto;
import com.example.demo01.domains.ProcessManagement.ProcessTag.dtos.processTag.ProcessTagNestFieldDto;
import com.example.demo01.domains.ProcessManagement.ProcessTag.dtos.processTag.ProcessTagRequest;
import com.example.demo01.domains.ProcessManagement.ProcessTag.models.ProcessTag;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProcessTagMapper {

    ProcessTag toProcessTag(ProcessTagRequest request);

    ProcessTagDto toProcessTagDto(ProcessTag processTag);

    List<ProcessTagDto> toProcessTagList(List<ProcessTag> processTags);

    ProcessTagNestFieldDto toProcessTagNestFieldDto(ProcessTag processTag);

    List<ProcessTagNestFieldDto> toProcessTagNestFieldDtoList(List<ProcessTag> processTags);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateProcessTagFromDto(ProcessTagRequest dto, @MappingTarget ProcessTag processTag);
}
