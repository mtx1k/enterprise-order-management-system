package com.final_project.ua_team_final_project.repositories;

import com.final_project.ua_team_final_project.models.Order;
import com.final_project.ua_team_final_project.models.OrderMessage;
import com.final_project.ua_team_final_project.models.SupplierOrder;
import com.final_project.ua_team_final_project.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderMessageRepository extends JpaRepository<OrderMessage, Long> {
    Optional<OrderMessage> findAllByMessageId(Long message_id);
    Optional<OrderMessage> findAllByUser(User user);
    Optional<OrderMessage> findAllByOrder(Order order);
    //Optional<OrderMessage> findAllBySupplierOrders(SupplierOrder supplierOrder);
}
