package com.example.demo01.core.Schedulers.config;

import java.util.Map;

public interface ScheduledTaskExecutor {
    void executeTask(String configId, Map<String, Object> params);
    String getTaskKey();
}
