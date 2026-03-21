package com.example.demo01.domains.MiniCrm.Renting.mapper;

import com.example.demo01.domains.MiniCrm.Renting.dtos.RentingAction;
import com.example.demo01.domains.MiniCrm.Renting.dtos.RentingDto;
import com.example.demo01.domains.MiniCrm.Renting.dtos.RentingPatchRequest;
import com.example.demo01.domains.MiniCrm.Renting.dtos.RentingRequest;
import com.example.demo01.domains.MiniCrm.Renting.model.RentingRecord;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RentingMapper {

    RentingRecord toEntity(RentingRequest request);

    RentingDto toDto(RentingRecord rentingRecord);

    RentingDto toDtoPreview(RentingRequest request);

    RentingPatchRequest toPatchRequest(RentingAction rentingAction);

    RentingRequest toRequest(RentingAction rentingAction);

    List<RentingDto> toDtoList(List<RentingRecord> rentingRecords);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateRentingFromDto(RentingPatchRequest patchRequest, @MappingTarget RentingRecord rentingRecord);
}
