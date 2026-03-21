package com.example.demo01.core.Schedulers.config;

import com.example.demo01.core.Schedulers.model.ScheduleConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TaskDispatcher {

    private final Map<String, ScheduledTaskExecutor> executors = new HashMap<>();

    public TaskDispatcher(List<ScheduledTaskExecutor> executorList) {
        if (executorList != null) {
            executorList.forEach(executor -> {
                executors.put(executor.getTaskKey(), executor);
            });
        }
    }

    public void dispatch(ScheduleConfig config) {
        String taskName = config.getTaskName();
        String configId = config.getId();
        Map<String, Object> params = config.getParams();

        ScheduledTaskExecutor executor = executors.get(config.getTaskName());

        if (executor != null) {
            executor.executeTask(configId, params);
        } else {
            throw new IllegalArgumentException("No executor found for task: " + taskName);
        }
    }
}
