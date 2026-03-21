package com.example.demo01.domains.MiniCrm.Contract.mappers;

import com.example.demo01.domains.MiniCrm.Contract.dtos.appendix.AppendixInfoDto;
import com.example.demo01.domains.MiniCrm.Contract.dtos.appendix.AppendixRequest;
import com.example.demo01.domains.MiniCrm.Contract.models.Appendix;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;


@Mapper(componentModel = "Spring")
public interface AppendixMapper {

    Appendix toEntity(AppendixRequest appendixRequest);

    Appendix toEntityFromDto(AppendixInfoDto appendixInfoDto);

    AppendixInfoDto toDto(Appendix appendix);

    List<AppendixInfoDto> toDtoList(List<Appendix> appendixList);

    AppendixInfoDto toDtoFromRequest(AppendixRequest appendixRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateContractFromEntity(AppendixRequest appendixRequest, @MappingTarget Appendix appendix);

}
