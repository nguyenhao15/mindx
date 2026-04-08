package com.example.demo01.core.Schedulers.service;

import com.example.demo01.core.Schedulers.dto.ScheduleConfigRequest;
import com.example.demo01.core.Schedulers.model.ScheduleConfig;
import com.example.demo01.repository.mongo.CoreRepo.SchedulesRepository.ScheduleConfigRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleConfigServiceImpl implements  ScheduleConfigService {

    private final ScheduleConfigRepository scheduleConfigRepository;

    @Override
    public void createOrUpdateConfig(ScheduleConfigRequest request) {
        ScheduleConfig scheduleConfig = scheduleConfigRepository.findByTaskName(request.getTaskName());
        if (scheduleConfig == null) {
            scheduleConfig = new ScheduleConfig();
        }
        scheduleConfig.setTaskName(request.getTaskName());
        scheduleConfig.setCronExpression(request.getCronExpression());
        scheduleConfig.setEnabled(request.getEnabled());
        scheduleConfig.setParams(request.getParams());

        scheduleConfigRepository.save(scheduleConfig);
    }
}
