package com.final_project.ua_team_final_project.services;

import com.final_project.ua_team_final_project.models.*;
import com.final_project.ua_team_final_project.repositories.AvailableProductsRepository;
import com.final_project.ua_team_final_project.repositories.OrderedProductRepository;
import com.final_project.ua_team_final_project.repositories.OrderRepository;
import com.final_project.ua_team_final_project.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AvailableProductsRepository availableProductsRepository;
    
    private final OrderRepository orderRepository;
    private final OrderedProductRepository orderedProductRepository;

    public OrderService(OrderRepository orderRepository, OrderedProductRepository orderedProductRepository) {
        this.orderRepository = orderRepository;
        this.orderedProductRepository = orderedProductRepository;
    }

    @Transactional
    public void saveOrder(Order order, List<OrderedProduct> orderedProducts) {
        orderRepository.save(order);

       
    }
    @Transactional
    public void saveNewOrder(Map<Long, Long> orderedProducts) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userRepository.findByName(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));

        Department department = user.getDepartment();

        Order order = new Order();
        order.setOrderId(1L);
        order.setDeptId(department);
        order.setStatusId(1L);
        order.setApprovedByHead(false);
        order.setApprovedByFinDept(false);
        order.setTotalPrice(0.0);


        for (Map.Entry<Long, Long> entry : orderedProducts.entrySet()) {

            Long productId = entry.getKey();
            Long quantity = entry.getValue();

            AvailableProducts product = availableProductsRepository
                    .findById(productId).orElseThrow(() -> new RuntimeException("Product not found " + productId));


            System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());

            OrderedProduct orderedProduct = new OrderedProduct();
            orderedProduct.setOrderedProductId(product.getProductId());
            orderedProduct.setOrder(order);
            orderedProduct.setName(product.getName());
            orderedProduct.setProductCode(product.getProductCode());
            orderedProduct.setItemPrice(product.getPrice() * quantity);
            orderedProduct.setCategoryId(product.getCategoryId());
            orderedProduct.setSupplierId(product.getSupplierId());
            orderedProduct.setAmount(quantity);


            order.addOrderedProduct(orderedProduct);
            order.setTotalPrice(order.getTotalPrice() + orderedProduct.getItemPrice());
            System.out.println("Order saved: " + order);
        }

        orderRepository.save(order);


    }


    public boolean setSelectedProductsModel(List<Long> selectedProducts, Map<String, String> quantities, Model model) {
        if (selectedProducts == null || selectedProducts.isEmpty()) {
            return true;
        }

        List<OrderedProduct> orderedProducts = new ArrayList<>();

        for (Long productId : selectedProducts) {
            String quantityStr = quantities.get("quantities[" + productId + "]");
            if (quantityStr != null && !quantityStr.isEmpty()) {
                AvailableProducts product = availableProductsRepository.findById(productId)
                        .orElseThrow(() -> new RuntimeException("Product not found: " + productId));

                OrderedProduct orderedProduct = new OrderedProduct();
                orderedProduct.setOrderedProductId(product.getProductId());
                orderedProduct.setProductCode(product.getProductCode());
                orderedProduct.setName(product.getName());
                orderedProduct.setItemPrice(product.getPrice());
                orderedProduct.setAmount((long) Integer.parseInt(quantityStr));
                orderedProducts.add(orderedProduct);
            }
        }

        if (orderedProducts.isEmpty()) {
            return true;
        }

        model.addAttribute("orderedProducts", orderedProducts);
        return false;
    }
}

