package com.final_project.ua_team_final_project.repositories;

import com.final_project.ua_team_final_project.models.SupplierOrder;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierOrderRepository extends JpaRepository<SupplierOrder, Long> {
    @NotNull Page<SupplierOrder> findAll(@NotNull Pageable pageable);

}
