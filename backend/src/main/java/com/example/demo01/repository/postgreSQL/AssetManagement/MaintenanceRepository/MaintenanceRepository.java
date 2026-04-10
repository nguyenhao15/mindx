package com.example.demo01.repository.postgreSQL.AssetManagement.MaintenanceRepository;

import com.example.demo01.domains.jpa.AssetManagement.Maintenance.entities.MaintenanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MaintenanceRepository extends JpaRepository<MaintenanceEntity, Long>,
        JpaSpecificationExecutor<MaintenanceEntity> {
}
