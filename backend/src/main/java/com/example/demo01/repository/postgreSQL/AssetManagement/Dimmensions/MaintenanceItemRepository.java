package com.example.demo01.repository.postgreSQL.AssetManagement.Dimmensions;

import com.example.demo01.domains.jpa.AssetManagement.Dimmensions.entities.MaintenanceItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaintenanceItemRepository extends JpaRepository<MaintenanceItemEntity, Long> {
}
