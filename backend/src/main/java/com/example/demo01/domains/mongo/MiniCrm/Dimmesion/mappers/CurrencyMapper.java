package com.example.demo01.domains.mongo.MiniCrm.Dimmesion.mappers;

import com.example.demo01.domains.mongo.MiniCrm.Dimmesion.dtos.Currency.CurrencyInfoDto;
import com.example.demo01.domains.mongo.MiniCrm.Dimmesion.dtos.Currency.CurrencyPatchRequest;
import com.example.demo01.domains.mongo.MiniCrm.Dimmesion.model.CurrencyDB;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CurrencyMapper {

    CurrencyDB toEntity(CurrencyPatchRequest currencyDTO);
    CurrencyInfoDto toDto(CurrencyDB currencyDB);
    List<CurrencyInfoDto> toDtos(List<CurrencyDB> currencyDBs);
    List<CurrencyDB> toEntities(List<CurrencyPatchRequest> currencyDTOs);
    void  updateCurrencyFromDto(CurrencyPatchRequest currencyDTO, @MappingTarget CurrencyDB currencyDB);

}
