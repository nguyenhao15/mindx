package com.example.demo01.core.Basement.mapper;

import com.example.demo01.core.Basement.dto.basement.BUInfoDto;
import com.example.demo01.core.Basement.dto.basement.BUPatchRequestDto;
import com.example.demo01.core.Basement.dto.basement.BURequestDto;
import com.example.demo01.core.Basement.model.BranchUnit;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BasementMapper {

    BranchUnit toEntity(BURequestDto requestDto);
    BUInfoDto toDto(BranchUnit branchUnit);

    List<BUInfoDto> toDtoList(List<BranchUnit> branchUnits);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateBasementFromDto(BUPatchRequestDto patchRequestDto , @MappingTarget BranchUnit branchUnit);

}
