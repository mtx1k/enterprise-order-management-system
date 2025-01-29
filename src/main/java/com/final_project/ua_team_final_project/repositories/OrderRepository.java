package com.final_project.ua_team_final_project.repositories;

import com.final_project.ua_team_final_project.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByOrderId(Long orderId);
    Optional<Order> findByDeptId(Long deptId);
    Optional<Order> findByTotalPrice(double totalPrice);
    Optional<Order> findByApprovedByHead(boolean approvedByHead);
    Optional<Order> findByCreatedAt(LocalDateTime createdAt);
    Optional<Order> findByUpdatedAt(LocalDateTime updatedAt);
    Optional<Order> findByStatusId(Long statusId);

}
