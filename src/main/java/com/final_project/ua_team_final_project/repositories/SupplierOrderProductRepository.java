package com.final_project.ua_team_final_project.repositories;

import com.final_project.ua_team_final_project.models.SupplierOrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SupplierOrderProductRepository extends JpaRepository<SupplierOrderProduct, Long> {
    Optional<SupplierOrderProduct> findBySupplierOrderId(Long supplierOrderId);
    Optional<SupplierOrderProduct> findAllBySupplierOrderId(Long supplierOrderId);
    Optional<SupplierOrderProduct> findByOrderProductId(Long orderProductId);
    Optional<SupplierOrderProduct> findAllByOrderProductId(Long orderProductId);
    Optional<SupplierOrderProduct> findAllByAmount(Long amount);
}
