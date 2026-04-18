package com.example.demo01.domains.jpa.Core.Approval.mapper;

import com.example.demo01.domains.jpa.Core.Approval.dto.Approval.ApprovalPolicyInfoDto;
import com.example.demo01.domains.jpa.Core.Approval.dto.Approval.ApprovalPolicyRequestDto;
import com.example.demo01.domains.jpa.Core.Approval.entities.ApprovalPolicyEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ApprovalPolicyMapper {

    ApprovalPolicyEntity fromRequestToEntity(ApprovalPolicyRequestDto requestDto);

    ApprovalPolicyInfoDto fromEntityToInfo(ApprovalPolicyEntity entity);

    List<ApprovalPolicyInfoDto> fromEntityListToInfoList(List<ApprovalPolicyEntity> entities);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromRequest(ApprovalPolicyRequestDto requestDto,@MappingTarget ApprovalPolicyEntity entity);

}
