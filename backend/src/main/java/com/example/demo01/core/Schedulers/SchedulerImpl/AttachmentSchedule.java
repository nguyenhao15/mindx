package com.example.demo01.core.Schedulers.SchedulerImpl;

import com.example.demo01.core.Attachment.service.AttachmentService;
import com.example.demo01.core.Schedulers.config.ScheduledTaskExecutor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AttachmentSchedule implements ScheduledTaskExecutor  {

    private final AttachmentService attachmentService;

    @Override
    public void executeTask(String configId, Map<String, Object> params) {
        attachmentService.permanentlyDeleteAttachment();
    }

    @Override
    public String getTaskKey() {
        return "CLEANUP_DELETED_ATTACHMENTS";
    }
}
