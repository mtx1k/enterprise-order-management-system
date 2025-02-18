package com.final_project.ua_team_final_project.repositories;

import com.final_project.ua_team_final_project.models.OrderStatus;
import com.final_project.ua_team_final_project.models.Supplier;
import com.final_project.ua_team_final_project.models.SupplierOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface SupplierOrdersRepository extends JpaRepository<SupplierOrder, Long> {
    Optional<SupplierOrder> findBySupplierOrderId(Long id);
    Optional<SupplierOrder> findAllBySupplierOrderId(Long supplierOrderId);
    Optional<SupplierOrder> findBySupplier(Supplier supplier);
    Optional<SupplierOrder> findAllBySupplier(Supplier supplier);
    Optional<SupplierOrder> findByTotalPrice(Double totalPrice);
    Optional<SupplierOrder> findAllByTotalPrice(Double totalPrice);
    Optional<SupplierOrder> findByCreatedAt(LocalDateTime createdAt);
    Optional<SupplierOrder> findAllByCreatedAt(LocalDateTime createdAt);
    Optional<SupplierOrder> findByOrderStatus(OrderStatus orderStatus);
    Optional<SupplierOrder> findAllByOrderStatus(OrderStatus orderStatus);
}
