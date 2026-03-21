package com.example.demo01.core.Schedulers.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ScheduleConfigRequest {

    private String taskName;
    private String cronExpression;
    private Map<String , Object> params;

    private Boolean enabled;

}
