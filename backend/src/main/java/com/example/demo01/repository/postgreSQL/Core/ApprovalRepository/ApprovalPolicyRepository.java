package com.example.demo01.repository.postgreSQL.Core.ApprovalRepository;

import com.example.demo01.domains.jpa.Core.Approval.entities.ApprovalPolicyEntity;
import com.example.demo01.utils.ModuleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApprovalPolicyRepository extends JpaRepository<ApprovalPolicyEntity, Long> {
    List<ApprovalPolicyEntity> findByCurrentStatusAndModule(String module, ModuleEnum moduleEnum);
}
