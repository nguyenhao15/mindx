package com.example.demo01.domains.jpa.Core.Approval.mapper;

import com.example.demo01.domains.jpa.AssetManagement.Dimmensions.mappers.MaintenanceItemMapper;
import com.example.demo01.domains.jpa.Core.Approval.dto.WorkFlowTransition.WorkFlowTransitionInfoDto;
import com.example.demo01.domains.jpa.Core.Approval.dto.WorkFlowTransition.WorkFlowTransitionRequestDto;
import com.example.demo01.domains.jpa.Core.Approval.entities.WorkFlowTransitionEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring",
        uses = {ApprovalPolicyMapper.class}
)
public interface WorkFlowTransitionMapper {

    WorkFlowTransitionEntity fromRequestToEntity(WorkFlowTransitionRequestDto workFlowTransitionRequestDto);

    WorkFlowTransitionInfoDto fromEntityToInfo(WorkFlowTransitionEntity workFlowTransitionEntity);

    List<WorkFlowTransitionInfoDto> fromEntityListToInfoList(List<WorkFlowTransitionEntity> entities);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromRequest(WorkFlowTransitionRequestDto requestDto,@MappingTarget WorkFlowTransitionEntity entity);

}
