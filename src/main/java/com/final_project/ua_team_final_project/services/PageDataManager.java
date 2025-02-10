package com.final_project.ua_team_final_project.services;

import com.final_project.ua_team_final_project.models.*;
import com.final_project.ua_team_final_project.repositories.*;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


@Component
@Service
public class PageDataManager {
    @Autowired
    private EntityManager entityManager;

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
    }

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
            setAdminModel(model, 1, 10, "userId", user);
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

    public void setFincoModel(Model model, User user) {
        model.addAttribute("user", user);
        List<Order> orders = orderRepository.findByApprovedByHeadTrueAndApprovedByFinDeptFalse();
        orders = orders.stream().filter(order -> order.getStatusId() != 4L).toList();
        model.addAttribute("orders", orders);
    }
}