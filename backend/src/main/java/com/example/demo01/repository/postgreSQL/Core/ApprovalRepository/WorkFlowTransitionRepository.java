package com.example.demo01.repository.postgreSQL.Core.ApprovalRepository;

import com.example.demo01.domains.jpa.Core.Approval.entities.WorkFlowTransitionEntity;
import com.example.demo01.utils.ModuleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkFlowTransitionRepository extends JpaRepository<WorkFlowTransitionEntity, Long>,
        JpaSpecificationExecutor<WorkFlowTransitionEntity> {
    List<WorkFlowTransitionEntity> findByFromStatusAndModule(String fromStatus, ModuleEnum module);
}
