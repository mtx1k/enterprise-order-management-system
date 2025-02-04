package com.final_project.ua_team_final_project.services;

import com.final_project.ua_team_final_project.models.*;
import com.final_project.ua_team_final_project.repositories.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Component
public class PageDataManager {
    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    private final RoleRepository roleRepository;
    private final AvailableProductsRepository availableProductsRepository;
    private final OrderRepository orderRepository;

    public PageDataManager(UserRepository userRepository, DepartmentRepository departmentRepository,
                           RoleRepository roleRepository, AvailableProductsRepository availableProductsRepository,
                           OrderRepository orderRepository) {
        this.roleRepository = roleRepository;
        this.departmentRepository = departmentRepository;
        this.userRepository = userRepository;
        this.availableProductsRepository = availableProductsRepository;
        this.orderRepository = orderRepository;
    }

    public void setAdminModel(Model model, User user) {
        model.addAttribute("user", user);
        model.addAttribute("users", userRepository.findAll());
    public void setAdminModel(Model model, Integer urlPageNumber, Integer pageSize, String order, User user) {

        int pageNumber = urlPageNumber - 1;

        Sort.Direction direction = Sort.Direction.ASC;

        if (order.endsWith("desc")) {
            direction = Sort.Direction.DESC;
            order = order.substring(0, order.length() - 5);
        }
        Sort sort = Sort.by(direction, order);
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<User> page = null;
        try {
            page = userRepository.findAll(pageable);
        } catch (PropertyReferenceException e) {
            setAdminModel(model, 1, 10, "userId");
        }
        if (page != null) {
            model.addAttribute("user", user);
            model.addAttribute("users", page.getContent());
            model.addAttribute("pageNumber", urlPageNumber);
            model.addAttribute("pageSize", pageSize);
            model.addAttribute("totalPages", page.getTotalPages());
            model.addAttribute("order", sort.toString());
        }
    }

    public void setEditUserModel(Long id, Model model, User user) {
        model.addAttribute("user", user);
        model.addAttribute("edituser", userRepository.findById(id).orElse(null));
        model.addAttribute("departments", departmentRepository.findAll());
        model.addAttribute("roles", roleRepository.findAll());
    }

    public void saveNewOrder(Map<String, String> orderItems) {
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
    }

    public boolean setSelectedProductsModel(List<Long> selectedProducts, Map<String, String> quantities, Model model) {
        if (selectedProducts == null || selectedProducts.isEmpty()) {
            return true;
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
            return true;
        }

        model.addAttribute("orderItems", orderItems);
        return false;
    }
}
