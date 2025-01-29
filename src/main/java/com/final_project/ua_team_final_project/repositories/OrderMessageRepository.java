package com.final_project.ua_team_final_project.repositories;

import com.final_project.ua_team_final_project.models.OrderMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderMessageRepository extends JpaRepository<OrderMessage, Long> {
    Optional<OrderMessage> findAllByMessageId(Long message_id);
    Optional<OrderMessage> findAllByUserId(Long userId);
    Optional<OrderMessage> findAllByOrderId(Long orderId);
    Optional<OrderMessage> findAllBySupplierOrderId(Long supplierOrderId);
}
