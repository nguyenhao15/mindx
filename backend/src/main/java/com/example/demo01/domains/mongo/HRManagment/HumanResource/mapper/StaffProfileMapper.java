package com.example.demo01.domains.mongo.HRManagment.HumanResource.mapper;

import com.example.demo01.domains.mongo.HRManagment.HumanResource.dto.StaffProfileInfoDto;
import com.example.demo01.domains.mongo.HRManagment.HumanResource.dto.StaffProfileRequestDto;
import com.example.demo01.domains.mongo.HRManagment.HumanResource.model.StaffProfileModels;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StaffProfileMapper {

    StaffProfileModels fromRequestToEntity(StaffProfileRequestDto requestDto);

    StaffProfileInfoDto fromEntityToDto(StaffProfileModels requestDto);

    List<StaffProfileInfoDto> fromEntitiesFromInFoDto(List<StaffProfileModels> entities);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateModelFromRequestDto(StaffProfileRequestDto requestDto, @MappingTarget StaffProfileModels entity);

}
