package com.example.demo01.domains.ProcessManagemnet.ProcessUtils;

import com.example.demo01.domains.HRManagment.Department.dto.WorkingField.WorkingFieldUpdate;
import com.example.demo01.domains.ProcessManagemnet.ProcessTag.dtos.processTag.ProcessTagUpdateRecord;
import com.example.demo01.domains.ProcessManagemnet.ProcessTag.dtos.processValue.ProcessTagValueUpdateRecord;
import org.springframework.context.event.EventListener;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.scheduling.annotation.Async;

public interface ProcessManagementUtil {

    Criteria buildAccessQuery();

    Criteria buildRestrictlyCriteria() ;

    Criteria buildLoosenCriteria();

    @EventListener
    @Async
    void handleProcessTagUpdate(ProcessTagUpdateRecord  updateRecord);

    @EventListener
    @Async
    void handleTagValueUpdate(ProcessTagValueUpdateRecord updateRecord);

    @EventListener
    @Async
    void handleUpdateWorkingField(WorkingFieldUpdate  workingFieldUpdate);
}
