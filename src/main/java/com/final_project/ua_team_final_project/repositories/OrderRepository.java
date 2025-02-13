package com.final_project.ua_team_final_project.repositories;

import com.final_project.ua_team_final_project.models.Department;
import com.final_project.ua_team_final_project.models.Order;
import com.final_project.ua_team_final_project.models.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByOrderId(Long orderId);
    List<Order> findByDept(Department dept);
    Optional<Order> findByTotalPrice(double totalPrice);
    List<Order> findByApprovedByHeadTrueAndApprovedByFinDeptFalse();
    Optional<Order> findByCreatedAt(LocalDateTime createdAt);
    Optional<Order> findByUpdatedAt(LocalDateTime updatedAt);
    Optional<Order> findByStatus(OrderStatus orderStatus);

    List<Order> findByApprovedByHeadTrueAndApprovedByFinDeptTrue();

    Page<Order> findByApprovedByHeadAndApprovedByFinDeptAndStatusNot(
            boolean approvedByHead, boolean approvedByFinDept, OrderStatus status, Pageable pageable);


}
