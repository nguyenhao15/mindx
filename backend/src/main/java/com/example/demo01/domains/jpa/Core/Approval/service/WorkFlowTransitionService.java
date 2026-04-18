package com.example.demo01.domains.jpa.Core.Approval.service;

import com.example.demo01.domains.jpa.Core.Approval.dto.WorkFlowTransition.WorkFlowTransitionInfoDto;
import com.example.demo01.domains.jpa.Core.Approval.dto.WorkFlowTransition.WorkFlowTransitionRequestDto;
import com.example.demo01.domains.jpa.Core.Approval.entities.WorkFlowTransitionEntity;

public interface WorkFlowTransitionService {

    WorkFlowTransitionInfoDto createWorkFlowTransition(WorkFlowTransitionRequestDto requestDto);

    WorkFlowTransitionEntity getWorkFlowTransitionById(Long id);

    WorkFlowTransitionInfoDto getWorkFlowTransitionDtoById(WorkFlowTransitionRequestDto requestDto);

    WorkFlowTransitionEntity updateWorkFlowTransition(Long id, WorkFlowTransitionEntity workFlowTransition);

    void deleteWorkFlowTransition(Long id);
}
