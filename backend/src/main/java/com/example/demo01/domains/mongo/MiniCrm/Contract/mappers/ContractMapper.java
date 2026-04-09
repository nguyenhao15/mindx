package com.example.demo01.domains.mongo.MiniCrm.Contract.mappers;

import com.example.demo01.domains.mongo.MiniCrm.Contract.models.Contract;
import com.example.demo01.domains.mongo.MiniCrm.Contract.dtos.contracts.ContractDTO;
import com.example.demo01.domains.mongo.MiniCrm.Contract.dtos.contracts.ContractPatchRequestDto;
import com.example.demo01.domains.mongo.MiniCrm.Contract.dtos.contracts.ContractRequestDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ContractMapper {

    Contract toEntity(ContractRequestDto requestDto);

    ContractDTO toDto(Contract contract);

    ContractPatchRequestDto toPatchRequestDto(ContractRequestDto requestDto);

    ContractDTO fromRequestToDTO(ContractRequestDto requestDto);

    Contract toEntityFromDTO(ContractDTO contractDTO);

    List<ContractDTO> toDTOList(List<Contract> contracts);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateContractFromDto(ContractPatchRequestDto dto, @MappingTarget Contract contract);

}
