package com.final_project.ua_team_final_project.controllers;


import com.final_project.ua_team_final_project.models.AvailableProducts;
import com.final_project.ua_team_final_project.models.Order;
import com.final_project.ua_team_final_project.models.OrderItem;
import com.final_project.ua_team_final_project.models.User;
import com.final_project.ua_team_final_project.repositories.*;
import com.final_project.ua_team_final_project.services.OrderService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AppController {

    private final OrderStatusRepository orderStatusRepository;
    private final UserRepository userRepository;
    private final AvailableProductsRepository availableProductsRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;


    public AppController(UserRepository userRepository, AvailableProductsRepository availableProductsRepository, OrderRepository orderRepository, OrderItemRepository orderItemRepository, OrderService orderService, OrderStatusRepository orderStatusRepository, OrderItemRepository orderItemRepository1) {
        this.userRepository = userRepository;
        this.availableProductsRepository = availableProductsRepository;
        this.orderRepository = orderRepository;
        this.orderStatusRepository = orderStatusRepository;
        this.orderItemRepository = orderItemRepository1;
    }

    @GetMapping("/")
    public String index(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            getAdminModel(model);
            return "/organization/adminpage";
        } else if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER"))) {
            getAvailableProductsModel(model);
            return "/organization/userpage";
        } else if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_HEAD"))) {
            return "/organization/pageofhead";
        } else if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_FINCO"))) {
            return "/organization/pageoffinco";
        } else if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_SUPPLIER"))) {
            return "/organization/supply";
        } else {
            return "redirect:/login";
        }
    }

    @PostMapping("/selectedProducts")
    public String selectedProducts(@RequestParam List<Long> selectedProducts,
                                   @RequestParam Map<String, String> quantities,
                                   Model model) {
        System.out.println("Selected Products: " + selectedProducts);
        System.out.println("Quantities: " + quantities);

        if (selectedProducts == null || selectedProducts.isEmpty()) {
            System.out.println("No products selected!");
            return "error";
        }

        List<AvailableProducts> selectedAvailableProducts = availableProductsRepository.findAllById(selectedProducts);
        List<OrderItem> orderItems = new ArrayList<>();
        for (AvailableProducts product : selectedAvailableProducts) {
            String quantityKey = "quantities[" + product.getProductId() + "]";
            if (quantities.containsKey(quantityKey) && !quantities.get(quantityKey).isEmpty()) {
                OrderItem orderItem = new OrderItem();
                orderItem.setProduct(product);
                orderItem.setQuantity(Integer.parseInt(quantities.get(quantityKey)));
                orderItems.add(orderItem);
            }
        }

        model.addAttribute("orderItems", orderItems);
        return "organization/editProducts";
    }




    private double calculateTotalPrice(Map<String, String> orderItems) {
        double totalPrice = 0.0;
        for (Map.Entry<String, String> entry : orderItems.entrySet()) {
            if (entry.getKey().contains("quantity")) {
                String productIdStr = entry.getKey().replace("orderItems[", "").replace("].quantity", "");
                try {
                    Long productId = Long.valueOf(productIdStr);
                    System.out.println("Checking product with ID: " + productId);
                    Integer quantity = Integer.valueOf(entry.getValue());
                    AvailableProducts product = availableProductsRepository.findById(productId)
                            .orElseThrow(() -> new RuntimeException("Product not found for ID: " + productId));
                    System.out.println("Found product: " + product.getProductId() + " - " + product.getProductCode());
                    totalPrice += product.getPrice() * quantity;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid number format for product ID: " + e.getMessage());
                }
            }
        }
        return totalPrice;
    }

    @PostMapping("/confirmOrder")
    public String confirmOrder(@RequestParam Map<String, String> orderItems, Model model) {
        System.out.println("Order items received: " + orderItems);

        Order order = new Order();
        order.setDeptId(1L);
        order.setTotalPrice(calculateTotalPrice(orderItems));
        order.setApprovedByHead(false);
        order.setApprovedByFinDept(false);

        Long statusId = 1L;
        if (!orderStatusRepository.existsById(statusId)) {
            throw new RuntimeException("Order status not found");
        }
        order.setStatusId(statusId);

        List<OrderItem> orderItemList = new ArrayList<>();
        for (Map.Entry<String, String> entry : orderItems.entrySet()) {
            if (entry.getKey().contains("quantity")) {
                String productIdStr = entry.getKey().replace("orderItems[", "").replace("].quantity", "");
                try {
                    Long productId = Long.valueOf(productIdStr);
                    System.out.println("Processing product with ID: " + productId);
                    Integer quantity = Integer.valueOf(entry.getValue());
                    AvailableProducts product = availableProductsRepository.findById(productId)
                            .orElseThrow(() -> new RuntimeException("Product not found for ID: " + productId));

                    OrderItem orderItem = new OrderItem();
                    orderItem.setOrder(order);
                    orderItem.setProduct(product);
                    orderItem.setQuantity(quantity);
                    orderItem.setPrice((double) (product.getPrice() * quantity));

                    orderItemList.add(orderItem);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid number format: " + e.getMessage());
                }
            }
        }

        order.setOrderItems(orderItemList);
        orderRepository.save(order);

        System.out.println("Order saved successfully: " + order);

        return "redirect:/";
    }



    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    private void getAdminModel(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
    }

    private void getAvailableProductsModel(Model model) {
        List<AvailableProducts> availableProducts = availableProductsRepository.findAll();
        model.addAttribute("availableProducts", availableProducts);

    }


}