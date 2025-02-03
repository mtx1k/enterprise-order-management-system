package com.final_project.ua_team_final_project.controllers;

import com.final_project.ua_team_final_project.models.*;
import com.final_project.ua_team_final_project.repositories.*;
import com.final_project.ua_team_final_project.services.OrderService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class AppController {

    private final OrderStatusRepository orderStatusRepository;
    private final UserRepository userRepository;
    private final AvailableProductsRepository availableProductsRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final DepartmentRepository departmentRepository;
Department department;

    public AppController(UserRepository userRepository, AvailableProductsRepository availableProductsRepository, OrderRepository orderRepository, OrderItemRepository orderItemRepository, OrderService orderService, OrderStatusRepository orderStatusRepository, DepartmentRepository departmentRepository) {
        this.userRepository = userRepository;
        this.availableProductsRepository = availableProductsRepository;
        this.orderRepository = orderRepository;
        this.orderStatusRepository = orderStatusRepository;
        this.orderItemRepository = orderItemRepository;
        this.departmentRepository = departmentRepository;

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
        if (selectedProducts == null || selectedProducts.isEmpty()) {
            return "redirect:/";
        }

        List<OrderItem> orderItems = new ArrayList<>();

        for (Long productId : selectedProducts) {
            String quantityStr = quantities.get("quantities[" + productId + "]");
            if (quantityStr != null && !quantityStr.isEmpty()) {
                AvailableProducts product = availableProductsRepository.findById(productId)
                        .orElseThrow(() -> new RuntimeException("Product not found: " + productId));

                OrderItem orderItem = new OrderItem();
                orderItem.setProduct(product);
                orderItem.setQuantity(Integer.parseInt(quantityStr));
                orderItems.add(orderItem);
            }
        }

        if (orderItems.isEmpty()) {
            return "redirect:/";
        }

        model.addAttribute("orderItems", orderItems);
        return "organization/editProducts";
    }

    @PostMapping("/confirmOrder")
    public String confirmOrder(@RequestParam Map<String, String> orderItems) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            User user = userRepository.findByName(username)
                    .orElseThrow(() -> new RuntimeException("User not found: " + username));

            Department department = user.getDepartment();

            Order order = new Order();
            order.setDeptId(department);
            order.setStatusId(1L);
            order.setApprovedByHead(false);
            order.setApprovedByFinDept(false);

            double totalPrice = 0.0;


            for (Map.Entry<String, String> entry : orderItems.entrySet()) {
                if (entry.getKey().matches("orderItems\\[(\\d+)\\].quantity")) {

                    String productId = entry.getKey().replaceAll("orderItems\\[(\\d+)\\].quantity", "$1");
                    Integer quantity = Integer.valueOf(entry.getValue());


                    AvailableProducts product = availableProductsRepository.findById(Long.valueOf(productId))
                            .orElseThrow(() -> new RuntimeException("Product not found: " + productId));

                    OrderItem orderItem = new OrderItem();
                    orderItem.setProduct(product);
                    orderItem.setQuantity(quantity);
                    orderItem.setPrice((double) (product.getPrice() * quantity));


                    order.addOrderItem(orderItem);
                    totalPrice += orderItem.getPrice();
                }
            }

            order.setTotalPrice(totalPrice);

            orderRepository.save(order);

            return "redirect:/";

        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
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