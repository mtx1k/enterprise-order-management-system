package com.final_project.ua_team_final_project.controllers;


import com.final_project.ua_team_final_project.models.*;
import com.final_project.ua_team_final_project.repositories.*;
import com.final_project.ua_team_final_project.models.User;
import com.final_project.ua_team_final_project.repositories.DepartmentRepository;
import com.final_project.ua_team_final_project.repositories.RoleRepository;
import com.final_project.ua_team_final_project.repositories.UserRepository;
import com.final_project.ua_team_final_project.services.OrderService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.final_project.ua_team_final_project.services.PageDataManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AppController {


    private final PageDataManager pageDataManager;
    private final UserRepository userRepository;
    private final DepartmentRepository deptRepository;
    private final RoleRepository roleRepository;
    private final AvailableProductsRepository availableProductsRepository;
    private final OrderService orderService;

    public AppController(PageDataManager pageDataManager, UserRepository userRepository,
                         DepartmentRepository deptRepository, RoleRepository roleRepository, AvailableProductsRepository availableProductsRepository, OrderService orderService) {
        this.pageDataManager = pageDataManager;
        this.userRepository = userRepository;
        this.deptRepository = deptRepository;
        this.roleRepository = roleRepository;
        this.availableProductsRepository = availableProductsRepository;
        this.orderService = orderService;
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
            pageDataManager.setAdminModel(model, urlPageNumber, pageSize, order, user);
            return "organization/adminpage";
        } else if ("USER".equals(user.getRole().getName())) {
            getAvailableProductsModel(model, user);
            return "organization/userpage";
        } else {
            return "accessDenied";
        }
    }

    @PostMapping("/selectedProducts")
    public String selectedProducts(@RequestParam List<Long> selectedProducts,
                                   @RequestParam Map<String, String> quantities,
                                   Model model,
                                   Principal principal) {
        if (orderService.setSelectedProductsModel(selectedProducts, quantities, model)) {
            return "redirect:/";
        }
        model.addAttribute("user", userRepository.findByName(principal.getName()).orElseThrow(() ->
                new UsernameNotFoundException("User not found: " + principal.getName())));
        return "organization/editProducts";
    }
    @GetMapping("/editProducts")
    public String editProducts(Model model) {
        OrderedProduct orderedProduct = new OrderedProduct();
        orderedProduct.setItemPrice(100.0);
        orderedProduct.setAmount(2L);
        model.addAttribute("orderedProduct", orderedProduct);
        return "organization/editProducts";
    }
    @PostMapping("/confirmOrder")
    public String confirmOrder(@RequestParam List<Long> selectedProducts,
                               @RequestParam List<Long> quantities) {
        Map<Long, Long> productQuantities = new HashMap<>();
        for (int i = 0; i < selectedProducts.size(); i++) {
            productQuantities.put(selectedProducts.get(i), quantities.get(i));

        }
        orderService.saveNewOrder(productQuantities);
//        try {
//
//            return "redirect:/";
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "error";
//        }

        // TODO: rewrite pageDataManager.saveNewOrder to user new Map of product ids and quantities
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

    @GetMapping("/useredit/{id}")
    public String editUser(@PathVariable Long id, Model model, Principal principal) {
        User user = userRepository.findByName(principal.getName()).orElseThrow(() ->
                new UsernameNotFoundException("User not found: " + principal.getName()));
        pageDataManager.setEditUserModel(id, model, user);
        return "editUser";
    }


    private void getAvailableProductsModel(Model model, User user) {
        List<AvailableProducts> availableProducts = availableProductsRepository.findAll();
        model.addAttribute("availableProducts", availableProducts);
        model.addAttribute("user", user);
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