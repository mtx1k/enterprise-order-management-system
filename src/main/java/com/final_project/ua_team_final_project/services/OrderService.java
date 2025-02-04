package com.final_project.ua_team_final_project.services;

import com.final_project.ua_team_final_project.models.Department;
import com.final_project.ua_team_final_project.models.Order;
import com.final_project.ua_team_final_project.models.OrderItem;
import com.final_project.ua_team_final_project.models.User;
import com.final_project.ua_team_final_project.repositories.OrderItemRepository;
import com.final_project.ua_team_final_project.repositories.OrderRepository;
import com.final_project.ua_team_final_project.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;

    public Department department;

    public OrderService(OrderRepository orderRepository, OrderItemRepository orderItemRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void saveOrder(Order order, List<OrderItem> orderItems) {
        orderRepository.save(order);

        for (OrderItem orderItem : orderItems) {
            orderItem.setOrder(order);
            orderItemRepository.save(orderItem);
        }
    }

    public List<Order> getOrdersForCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        return userRepository.findByName(username)
                .map(user -> orderRepository.findByDeptId(user.getDepartment())).orElse(List.of());
        }
}

