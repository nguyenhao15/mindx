package com.example.demo01.core.Schedulers.config;

import com.example.demo01.core.EmailService.EmailSenderService;
import com.example.demo01.core.Schedulers.model.ScheduleConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

@Component
@RequiredArgsConstructor
public class DynamicScheduleManager {

    private final ThreadPoolTaskScheduler taskScheduler;
    private final TaskDispatcher taskDispatcher;
    private final EmailSenderService emailSenderService;

    private final Map<String, ScheduledFuture<?>> scheduledTasks = new ConcurrentHashMap<>();


    public void scheduleTask(ScheduleConfig config) {
        cancelTask(config.getId());
        if (!config.isEnabled() || config.getCronExpression() == null) return;

        Runnable task = () -> {
            try {
                taskDispatcher.dispatch(config);
            } catch (Exception e) {
                e.printStackTrace();
                emailSenderService.sendEmail("lynguyenhao15@gmail.com", "Scheduled Task Error", "An error occurred while executing scheduled task with ID: " + config.getId() + "\nError: " + e.getMessage());
            }
        };

        try {
            ScheduledFuture<?> future = taskScheduler.schedule(task, new CronTrigger(config.getCronExpression()));
            scheduledTasks.put(config.getId(), future);
        } catch (Exception e) {
            // Lỗi do Cron sai định dạng
            System.out.println("Cron expression invalid: {}" + config.getCronExpression());
        }

    }

    public void cancelTask(String configId) {
        ScheduledFuture<?> future = scheduledTasks.get(configId);
        if (future != null) {
            future.cancel(true);
            scheduledTasks.remove(configId);
        }
    }

}
