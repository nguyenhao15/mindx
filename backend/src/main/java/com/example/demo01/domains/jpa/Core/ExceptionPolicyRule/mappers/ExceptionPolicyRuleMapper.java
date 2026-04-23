package com.example.demo01.domains.jpa.Core.ExceptionPolicyRule.mappers;

import com.example.demo01.domains.jpa.Core.ExceptionPolicyRule.dtos.ExceptionPolicyRuleInfoDto;
import com.example.demo01.domains.jpa.Core.ExceptionPolicyRule.dtos.ExceptionPolicyRuleRequestDto;
import com.example.demo01.domains.jpa.Core.ExceptionPolicyRule.entity.ExceptionPolicyRuleEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "Spring")
public interface ExceptionPolicyRuleMapper {

    ExceptionPolicyRuleEntity fromRequestToEntity(ExceptionPolicyRuleRequestDto policyRuleInfoDto);
    ExceptionPolicyRuleInfoDto fromEntityToDto(ExceptionPolicyRuleEntity exceptionPolicyRuleEntity);

    List<ExceptionPolicyRuleInfoDto> fromEntitiesToDtos(List<ExceptionPolicyRuleEntity> policyRuleEntities);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(ExceptionPolicyRuleRequestDto requestDto, @MappingTarget ExceptionPolicyRuleEntity exceptionPolicyRuleEntity);

}
