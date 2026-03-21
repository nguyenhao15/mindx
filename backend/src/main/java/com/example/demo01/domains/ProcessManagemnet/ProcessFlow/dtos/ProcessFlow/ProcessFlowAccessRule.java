package com.example.demo01.domains.ProcessManagemnet.ProcessFlow.dtos.ProcessFlow;

import com.example.demo01.domains.ProcessManagemnet.ProcessFlow.model.ACCESSPOLICY;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProcessFlowAccessRule {

    private ACCESSPOLICY accessType;
    private List<String> departmentCodes;

    private List<String> positionCodes;
    private List<String> buIds;
    private List<String> fieldIds;

    private Integer minLevel;
    private List<String> allowedUserIds;
}
