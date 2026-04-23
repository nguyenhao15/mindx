package com.example.demo01.repository.postgreSQL.Core.PolicyRuleRepository;

import com.example.demo01.domains.jpa.Core.ExceptionPolicyRule.entity.ExceptionPolicyRuleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PolicyRuleRepository extends JpaRepository<ExceptionPolicyRuleEntity, Long> {

    List<ExceptionPolicyRuleEntity> findByStaffId(String staffId);

}
