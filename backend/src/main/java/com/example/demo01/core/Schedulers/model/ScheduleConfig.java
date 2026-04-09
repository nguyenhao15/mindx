package com.example.demo01.core.Schedulers.model;


import com.example.demo01.utils.BaseModels.BaseAuditModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Document(collection = "scheduleConfigs")
public class ScheduleConfig extends BaseAuditModel {

    @Id
    private String id;

    private String taskName;
    private String cronExpression;

    private Map<String, Object> params;

    private boolean enabled = true;

}
