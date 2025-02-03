package com.final_project.ua_team_final_project.controllers;


import com.final_project.ua_team_final_project.models.*;
import com.final_project.ua_team_final_project.repositories.*;

import com.final_project.ua_team_final_project.models.Role;
import com.final_project.ua_team_final_project.models.User;
import com.final_project.ua_team_final_project.repositories.DepartmentRepository;
import com.final_project.ua_team_final_project.repositories.RoleRepository;
import com.final_project.ua_team_final_project.repositories.UserRepository;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.final_project.ua_team_final_project.services.PageDataManager;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;

import java.time.LocalDate;
import java.time.LocalDateTime;

import java.security.Principal;

import java.util.List;
import java.util.Map;

@Controller
public class AppController {


    private final PageDataManager pageDataManager;
    private final UserRepository userRepository;
    private final DepartmentRepository deptRepository;
    private final RoleRepository roleRepository;
    private final AvailableProductsRepository availableProductsRepository;
    private final OrderRepository orderRepository;

    public AppController(PageDataManager pageDataManager, UserRepository userRepository,
                         DepartmentRepository deptRepository, RoleRepository roleRepository, AvailableProductsRepository availableProductsRepository, OrderRepository orderRepository) {
        this.pageDataManager = pageDataManager;
        this.userRepository = userRepository;
        this.deptRepository = deptRepository;
        this.roleRepository = roleRepository;
        this.availableProductsRepository = availableProductsRepository;
        this.orderRepository = orderRepository;
    }

    @GetMapping("/")
    public String index(Principal principal, Model model) {
        if (principal == null) {

            return "redirect:/login";
        }

        User user = userRepository.findByName(principal.getName()).orElseThrow(() ->
                new UsernameNotFoundException("User not found: " + principal.getName()));

        if (user == null) {
            return "redirect:/login";
        }

        if ("ADMIN".equals(user.getRole().getName())) {
            pageDataManager.setAdminModel(model);
            return "organization/adminpage";
        } else if ("USER".equals(user.getRole().getName())) {
            getAvailableProductsModel(model);
            return "organization/userpage";
        } else {
            return "accessDenied";
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

    @GetMapping("/useredit/{id}")
    public String editUser(@PathVariable Long id, Model model) {
        pageDataManager.setEditUserModel(id, model);
        return "editUser";
    }


    private void getAvailableProductsModel(Model model) {
        List<AvailableProducts> availableProducts = availableProductsRepository.findAll();
        model.addAttribute("availableProducts", availableProducts);

    }


    @PostMapping("/edituser")
    public String editUser(@RequestParam("id") Long id,
                           @RequestParam("department") String department,
                           @RequestParam("name") String name,
                           @RequestParam("role") String role,
                           @RequestParam("login") String login,
                           @RequestParam("phone") String phone,
                           @RequestParam("email") String email,
                           @RequestParam("hired_at") LocalDate created_at) {

        User user = new User();
        user.setUserId(id);
        user.setDepartment(deptRepository.findByName(department).orElse(null));
        user.setName(name);
        user.setLogin(login);
        user.setPhone(phone);
        user.setEmail(email);
        user.setCreatedAt(created_at.atStartOfDay());
        user.setRole(roleRepository.findByName(role).orElse(null));
        user.setPasswordEnc(userRepository.findById(id).orElseThrow(IllegalArgumentException::new).getPasswordEnc());

        userRepository.save(user);
        return "redirect:/";
    }

    @GetMapping("/deleteuser/{id}")
    public String deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return "redirect:/";
    }
}
