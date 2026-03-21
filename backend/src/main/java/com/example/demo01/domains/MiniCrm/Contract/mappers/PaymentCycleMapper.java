package com.example.demo01.domains.MiniCrm.Contract.mappers;


import com.example.demo01.domains.MiniCrm.Contract.dtos.paymentCycles.PaymentCyclePatchRequestDto;
import com.example.demo01.domains.MiniCrm.Contract.models.PaymentCycle;
import com.example.demo01.domains.MiniCrm.Contract.dtos.paymentCycles.PaymentCycleDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PaymentCycleMapper {

    PaymentCycle toEntity(PaymentCycleDTO paymentCycleDTO);

    PaymentCycleDTO toDTO(PaymentCycle paymentCycle);

    List<PaymentCycleDTO> toDTOList(List<PaymentCycle> paymentCycles);

    List<PaymentCycle> toEntityList(List<PaymentCycleDTO> paymentCycleDTOList);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updatePaymentCycle(PaymentCyclePatchRequestDto dto, @MappingTarget PaymentCycle paymentCycle);
}
