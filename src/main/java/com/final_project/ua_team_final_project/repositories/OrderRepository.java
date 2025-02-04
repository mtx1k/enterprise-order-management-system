package com.final_project.ua_team_final_project.repositories;

import com.final_project.ua_team_final_project.models.Department;
import com.final_project.ua_team_final_project.models.Order;
import com.final_project.ua_team_final_project.models.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByOrderId(Long orderId);
    List<Order> findByDeptId(Department deptId);
    Optional<Order> findByTotalPrice(double totalPrice);
    Optional<Order> findByApprovedByHead(boolean approvedByHead);
    Optional<Order> findByCreatedAt(LocalDateTime createdAt);
    Optional<Order> findByUpdatedAt(LocalDateTime updatedAt);
    Optional<Order> findByStatusId(OrderStatus statusId);
}
