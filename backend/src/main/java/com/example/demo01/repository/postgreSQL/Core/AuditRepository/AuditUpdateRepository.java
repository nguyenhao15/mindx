package com.example.demo01.repository.postgreSQL.Core.AuditRepository;

import com.example.demo01.domains.jpa.Core.Audit.entity.AuditUpdateEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditUpdateRepository extends JpaRepository<AuditUpdateEntity, Long> {

    List<AuditUpdateEntity> findByIdentifier(String identifier, Sort sort);

    List<AuditUpdateEntity> findByIdentifier(String identifier);

}
