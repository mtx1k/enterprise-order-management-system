package com.final_project.ua_team_final_project.services;

import com.final_project.ua_team_final_project.models.Order;
import com.final_project.ua_team_final_project.models.OrderItem;
import com.final_project.ua_team_final_project.repositories.OrderItemRepository;
import com.final_project.ua_team_final_project.repositories.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    public OrderService(OrderRepository orderRepository, OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
    }

    @Transactional
    public void saveOrder(Order order, List<OrderItem> orderItems) {
        orderRepository.save(order);

        for (OrderItem orderItem : orderItems) {
            orderItem.setOrder(order);
            orderItemRepository.save(orderItem);
        }
    }
}

