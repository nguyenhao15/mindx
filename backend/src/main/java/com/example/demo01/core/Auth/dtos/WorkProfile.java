package com.example.demo01.core.Auth.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkProfile {

    private String uuid = UUID.randomUUID().toString().substring(0,4);
    private String departmentId;
    private String positionCode;
    private Boolean isMainPosition;
    private int positionLevel;
    private List<String> buAllowedList;

}
