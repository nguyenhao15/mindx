package com.example.demo01.core.Schedulers.controller;

import com.example.demo01.core.Schedulers.dto.ScheduleConfigRequest;
import com.example.demo01.core.Schedulers.service.ScheduleConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/scheduler")
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class SchedulerController {

    private final ScheduleConfigService scheduleConfigService;

    @PostMapping
    public ResponseEntity<?> createOrUpdateScheduler(@RequestBody ScheduleConfigRequest scheduleConfigRequest) {
        scheduleConfigService.createOrUpdateConfig(scheduleConfigRequest);
        return ResponseEntity.ok("Scheduler configuration saved");
    }

}
