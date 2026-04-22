package com.example.demo01.domains.jpa.Core.Approval.service;

import com.example.demo01.domains.jpa.Core.Approval.dto.WorkFlowTransition.WorkFlowTransitionInfoDto;
import com.example.demo01.domains.jpa.Core.Approval.dto.WorkFlowTransition.WorkFlowTransitionRequestDto;
import com.example.demo01.domains.jpa.Core.Approval.entities.WorkFlowTransitionEntity;
import com.example.demo01.utils.BasePageResponse;
import com.example.demo01.utils.FilterWithPagination;
import com.example.demo01.utils.ModuleEnum;
import org.springframework.data.domain.Page;

import java.util.List;

public interface WorkFlowTransitionService {

    WorkFlowTransitionInfoDto createWorkFlowTransition(WorkFlowTransitionRequestDto requestDto);

    WorkFlowTransitionEntity getWorkFlowTransitionById(Long id);

    WorkFlowTransitionInfoDto getWorkFlowTransitionDtoById(Long id);

    List<WorkFlowTransitionInfoDto> getWorkFlowTransitionDtoByCurrentStatusAndModule(String currentStatus, ModuleEnum moduleEnum);

    List<WorkFlowTransitionInfoDto> getWorkFlowTransitionDtoByModule(ModuleEnum moduleEnum);

    BasePageResponse<WorkFlowTransitionInfoDto> getAllPageWorkFlowTransitionDto(FilterWithPagination filter);

    BasePageResponse<WorkFlowTransitionInfoDto> getWorkFlowTransitionDtoByPage(FilterWithPagination filter);

    BasePageResponse<WorkFlowTransitionInfoDto> buildPageResponse(Page<WorkFlowTransitionEntity> page);

    WorkFlowTransitionInfoDto updateWorkFlowTransition(Long id, WorkFlowTransitionRequestDto workFlowTransition);

    void deleteWorkFlowTransition(Long id);
}
