package com.final_project.ua_team_final_project.services;

import com.final_project.ua_team_final_project.models.Order;
import com.final_project.ua_team_final_project.models.OrderedProduct;
import com.final_project.ua_team_final_project.repositories.OrderedProductRepository;
import com.final_project.ua_team_final_project.repositories.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderedProductRepository orderedProductRepository;

    public OrderService(OrderRepository orderRepository, OrderedProductRepository orderedProductRepository) {
        this.orderRepository = orderRepository;
        this.orderedProductRepository = orderedProductRepository;
    }

    @Transactional
    public void saveOrder(Order order, List<OrderedProduct> orderedProducts) {
        orderRepository.save(order);

        for (OrderedProduct orderedProduct : orderedProducts) {
            orderedProduct.setOrder(order);
            orderedProductRepository.save(orderedProduct);
        }
    }
}

