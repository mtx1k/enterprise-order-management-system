package com.final_project.ua_team_final_project.repositories;

import com.final_project.ua_team_final_project.models.OrderStatus;
import com.final_project.ua_team_final_project.models.Supplier;
import com.final_project.ua_team_final_project.models.SupplierOrders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface SupplierOrdersRepository extends JpaRepository<SupplierOrders, Long> {
    Optional<SupplierOrders> findBySupplierOrderId(Long id);
    Optional<SupplierOrders> findAllBySupplierOrderId(Long supplierOrderId);
    Optional<SupplierOrders> findBySupplier(Supplier supplier);
    Optional<SupplierOrders> findAllBySupplier(Supplier supplier);
    Optional<SupplierOrders> findByTotalPrice(Double totalPrice);
    Optional<SupplierOrders> findAllByTotalPrice(Double totalPrice);
    Optional<SupplierOrders> findByCreatedAt(LocalDateTime createdAt);
    Optional<SupplierOrders> findAllByCreatedAt(LocalDateTime createdAt);
    Optional<SupplierOrders> findByOrderStatus(OrderStatus orderStatus);
    Optional<SupplierOrders> findAllByOrderStatus(OrderStatus orderStatus);
}
