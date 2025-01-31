package com.final_project.ua_team_final_project.repositories;

import com.final_project.ua_team_final_project.models.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
