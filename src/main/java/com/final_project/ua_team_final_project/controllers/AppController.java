package com.final_project.ua_team_final_project.controllers;


import com.final_project.ua_team_final_project.models.*;
import com.final_project.ua_team_final_project.repositories.*;
import com.final_project.ua_team_final_project.models.User;
import com.final_project.ua_team_final_project.repositories.DepartmentRepository;
import com.final_project.ua_team_final_project.repositories.RoleRepository;
import com.final_project.ua_team_final_project.repositories.UserRepository;
import com.final_project.ua_team_final_project.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.final_project.ua_team_final_project.services.PageDataManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class AppController {


    private final PageDataManager pageDataManager;
    private final UserRepository userRepository;
    private final DepartmentRepository deptRepository;
    private final RoleRepository roleRepository;
    private final AvailableProductsRepository availableProductsRepository;
    private final OrderRepository orderRepository;

    @Autowired
    public OrderService orderService;

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
    public String index(Principal principal,
                        @RequestParam(name = "page", required = false, defaultValue = "1") Integer urlPageNumber,
                        @RequestParam(name = "page_size", required = false, defaultValue = "10") Integer pageSize,
                        @RequestParam(name = "order", required = false, defaultValue = "userId") String order,
                        Model model) {
        if (principal == null) {

            return "redirect:/login";
        }

        User user = userRepository.findByName(principal.getName()).orElseThrow(() ->
                new UsernameNotFoundException("User not found: " + principal.getName()));

        if (user == null) {
            return "redirect:/login";
        }

        if ("ADMIN".equals(user.getRole().getName())) {
            pageDataManager.setAdminModel(model, urlPageNumber, pageSize, order);
            return "organization/adminPage";
        } else if ("USER".equals(user.getRole().getName())) {
            getAvailableProductsModel(model);
            return "organization/userPage";
        } else if ("HEAD".equals(user.getRole().getName())) {
            List<Order> orderForDept = orderService.getOrdersForCurrentUser();
            if (orderForDept.isEmpty()) {
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            } else {
                ResponseEntity.ok(orderForDept);
            }


            model.addAttribute("orderForDept", orderForDept);
            model.addAttribute("department", user.getDepartment().getName());
            return "organization/pageOfHead";
        } else {
            return "accessDenied";
        }
    }

    @PostMapping("/selectedProducts")
    public String selectedProducts(@RequestParam List<Long> selectedProducts,
                                   @RequestParam Map<String, String> quantities,
                                   Model model) {
        if (pageDataManager.setSelectedProductsModel(selectedProducts, quantities, model)) {
            return "redirect:/";
        }
        return "organization/editProducts";
    }

    @PostMapping("/confirmOrder")
    public String confirmOrder(@RequestParam Map<String, String> orderItems) {
        try {
            pageDataManager.saveNewOrder(orderItems);
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

    private void getOrdersFromDepartment(Model model) {
        List<Order> orders = orderRepository.findAll();
        model.addAttribute("orders", orders);
    }



//    @GetMapping
//    String profilePage(Principal principal, Model model) {
//        String username = SecurityContextHolder.getContext().getAuthentication().getName();
//        Optional<User> user = userRepository.findByName(username);
//        model.addAttribute("department", user.get().getDepartment().getName());
//        return "profile";
//    }

    @PostMapping("/editUser")
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
