package com.example.demo01.core.Auth.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkProfile {

    private String departmentId;
    private String positionCode;
    private Boolean isMainPosition;
    private int positionLevel;
    private List<String> buAllowedList;

    // Trong class WorkProfile
    public WorkProfile(WorkProfile dto) {
        this.departmentId = dto.getDepartmentId();
        this.positionCode = dto.getPositionCode();
        this.isMainPosition = dto.getIsMainPosition();
        this.positionLevel = dto.getPositionLevel();
        this.buAllowedList = dto.getBuAllowedList();
    }

}
