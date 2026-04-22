package com.example.demo01.utils.Query.PostgreSQL;

import com.example.demo01.domains.jpa.Core.Approval.dto.WorkFlowTransition.WorkFlowTransitionInfoDto;
import com.example.demo01.domains.jpa.Core.Approval.service.ApprovalPolicyService;
import com.example.demo01.domains.jpa.Core.Approval.service.WorkFlowTransitionService;
import com.example.demo01.utils.ModuleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ApprovalEngineUtil {

    @Autowired
    private ApprovalPolicyService approvalPolicyService;

    @Autowired
    private WorkFlowTransitionService workFlowTransitionService;

    public List<ActionResponse> getAvailableAction(String currentStatus,String fromDepartment,String author , ModuleEnum moduleEnum) {
        List<WorkFlowTransitionInfoDto> availableAction = workFlowTransitionService.getWorkFlowTransitionDtoByCurrentStatusAndModule(currentStatus, moduleEnum);
        if (availableAction == null || availableAction.isEmpty()) {
            return List.of();
        }
        List<WorkFlowTransitionInfoDto> filteredArr = availableAction.stream()
                .filter(action -> approvalPolicyService.getExactRule(action.toStatus(), fromDepartment, moduleEnum, author ))
                .distinct()
                .toList();

        List<ActionResponse> actionResponseList = new ArrayList<>();
        for (WorkFlowTransitionInfoDto dto : filteredArr) {
            ActionResponse actionResponse =  new ActionResponse( dto.labelName(),dto.toStatus(), dto.actionType());
            actionResponseList.add(actionResponse);
        }
        return actionResponseList;
    }


}

