package com.example.demo01.repository.postgreSQL.AssetManagement.MaintenanceRepository;

import com.example.demo01.domains.jpa.AssetManagement.Maintenance.entities.MaintenancesProposals;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaintenancesProposalRepository extends JpaRepository<MaintenancesProposals, Long> {
}
