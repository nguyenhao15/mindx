package com.example.demo01.core.SpaceCustomer.mapper;

import com.example.demo01.core.SpaceCustomer.models.CustomerInfo;
import com.example.demo01.core.SpaceCustomer.payload.CustomerInfo.CustomerInfoDTO;
import com.example.demo01.core.SpaceCustomer.payload.CustomerInfo.CustomerPatchRequestDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerInfoMapper {


    CustomerInfoDTO toDTO(CustomerInfo customerInfo);

    List<CustomerInfoDTO> toDTOList(List<CustomerInfo> customerInfos);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCustomerFromDto(CustomerPatchRequestDTO dto, @MappingTarget CustomerInfo customerInfo);

}
