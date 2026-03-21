package com.example.demo01.domains.MiniCrm.Contract.mappers;


import com.example.demo01.domains.MiniCrm.Contract.dtos.terminateCollection.TerminateCollectionInfoDto;
import com.example.demo01.domains.MiniCrm.Contract.dtos.terminateCollection.TerminateRequest;
import com.example.demo01.domains.MiniCrm.Contract.models.TerminateModel;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TerminateUpdateCollectionMapper {

    TerminateModel toEntity(TerminateRequest terminateRequest);

    TerminateCollectionInfoDto toDTO(TerminateModel terminateModel);

    List<TerminateCollectionInfoDto> toDTO(List<TerminateModel> terminateModels);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateTerminateModel(TerminateRequest dto, @MappingTarget TerminateModel terminateModel);

}
