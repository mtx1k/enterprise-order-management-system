package com.final_project.ua_team_final_project.repositories;

import com.final_project.ua_team_final_project.models.OrderedProduct;
import com.final_project.ua_team_final_project.models.SupplierOrderProduct;
import com.final_project.ua_team_final_project.models.SupplierOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SupplierOrderProductRepository extends JpaRepository<SupplierOrderProduct, Long> {
    Optional<SupplierOrderProduct> findBySupplierOrder(SupplierOrder supplierOrder);
    Optional<SupplierOrderProduct> findAllBySupplierOrder(SupplierOrder supplierOrder);
    Optional<SupplierOrderProduct> findByOrderProduct(OrderedProduct orderProduct);
    Optional<SupplierOrderProduct> findAllByOrderProduct(OrderedProduct orderProduct);
    Optional<SupplierOrderProduct> findAllByAmount(Long amount);
}
