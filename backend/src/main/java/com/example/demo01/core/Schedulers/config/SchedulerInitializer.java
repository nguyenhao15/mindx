package com.example.demo01.core.Schedulers.config;

import com.example.demo01.core.Schedulers.repository.ScheduleConfigRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SchedulerInitializer implements CommandLineRunner {

    private final ScheduleConfigRepository scheduleConfigRepository;

    private final DynamicScheduleManager dynamicScheduleManager;

    @Override
    public void run(String... args) throws Exception {
        scheduleConfigRepository.findByEnabledTrue().forEach(dynamicScheduleManager::scheduleTask);
    }
}
