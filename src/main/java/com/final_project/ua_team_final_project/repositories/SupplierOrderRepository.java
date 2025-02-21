package com.final_project.ua_team_final_project.repositories;

import com.final_project.ua_team_final_project.models.Supplier;
import com.final_project.ua_team_final_project.models.SupplierOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface SupplierOrderRepository extends JpaRepository<SupplierOrder, Long> {
}
