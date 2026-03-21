package com.example.demo01.core.Schedulers.service;

import com.example.demo01.core.Schedulers.dto.ScheduleConfigRequest;

public interface ScheduleConfigService {

    void createOrUpdateConfig(ScheduleConfigRequest request);

}
