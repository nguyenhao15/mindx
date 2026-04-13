package com.example.demo01.repository.postgreSQL.AssetManagement.Dimmensions;

import com.example.demo01.domains.jpa.AssetManagement.Dimmensions.entities.MaintenanceCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MaintenanceCategoryRepository extends JpaRepository<MaintenanceCategoryEntity, Long> {

    List<MaintenanceCategoryEntity> findByActive(Boolean active);
}
