package com.final_project.ua_team_final_project.repositories;

import com.final_project.ua_team_final_project.models.AvailableProducts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AvailableProductsRepository extends JpaRepository<AvailableProducts, Long>, JpaSpecificationExecutor<AvailableProducts> {
    Page<AvailableProducts> findByNameContainingIgnoreCase(String productName, Pageable pageable);
}
