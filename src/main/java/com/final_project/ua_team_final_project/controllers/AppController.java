package com.final_project.ua_team_final_project.controllers;


import com.final_project.ua_team_final_project.models.*;
import com.final_project.ua_team_final_project.repositories.*;
import com.final_project.ua_team_final_project.models.User;
import com.final_project.ua_team_final_project.repositories.DepartmentRepository;
import com.final_project.ua_team_final_project.repositories.RoleRepository;
import com.final_project.ua_team_final_project.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.final_project.ua_team_final_project.services.PageDataManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.security.Principal;
import java.util.ArrayList;
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
    private final OrderedProductRepository orderedProductRepository;
    private final OrderRepository orderRepository;

    public AppController(PageDataManager pageDataManager, UserRepository userRepository, DepartmentRepository deptRepository, RoleRepository roleRepository, AvailableProductsRepository availableProductsRepository, OrderedProductRepository orderedProductRepository, OrderRepository orderRepository) {
        this.pageDataManager = pageDataManager;
        this.userRepository = userRepository;
        this.deptRepository = deptRepository;
        this.roleRepository = roleRepository;
        this.availableProductsRepository = availableProductsRepository;
        this.orderedProductRepository = orderedProductRepository;
        this.orderRepository = orderRepository;
    }

    @GetMapping("/")
    public String index(Principal principal, @RequestParam(name = "page", required = false, defaultValue = "1") Integer urlPageNumber, @RequestParam(name = "page_size", required = false, defaultValue = "10") Integer pageSize, @RequestParam(name = "order", required = false, defaultValue = "userId") String order, Model model) {
        if (principal == null) {

            return "redirect:/login";
        }

        User user = userRepository.findByName(principal.getName()).orElseThrow(() -> new UsernameNotFoundException("User not found: " + principal.getName()));

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

    @PostMapping("/add-to-order")
    public String addToOrder(@RequestParam(value = "productIds", required = false) List<Long> productIds, @RequestParam(value = "quantities", required = false) List<Long> quantities, RedirectAttributes redirectAttributes) {

        if (productIds == null || quantities == null || productIds.size() != quantities.size() || productIds.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Пожалуйста, выберите товары и укажите количество.");
            return "redirect:/";
        }

        List<OrderedProduct> orderedProducts = new ArrayList<>();
        for (int i = 0; i < productIds.size(); i++) {
            Long productId = productIds.get(i);
            Long quantity = quantities.get(i);

            AvailableProducts product = availableProductsRepository.findById(productId).orElse(null);
            if (product != null && quantity > 0) {
                OrderedProduct orderedProduct = new OrderedProduct();
                orderedProduct.setProductCode(product.getProductCode());
                orderedProduct.setName(product.getName());
                orderedProduct.setItemPrice(product.getPrice().doubleValue());
                orderedProduct.setCategoryId(product.getCategoryId());
                orderedProduct.setSupplierId(product.getSupplierId());
                orderedProduct.setAmount(quantity);
                orderedProducts.add(orderedProduct);
            }
        }

        redirectAttributes.addFlashAttribute("orderedProducts", orderedProducts);
        return "redirect:/order-confirmation";
    }


    @GetMapping("/order-confirmation")
    public String orderConfirmation(Model model, @ModelAttribute("orderedProducts") List<OrderedProduct> orderedProducts) {
        if (orderedProducts == null || orderedProducts.isEmpty()) {
            return "redirect:/";
        }
        model.addAttribute("orderedProducts", orderedProducts);
        return "/organization/order_confirmation";
    }

    @PostMapping("/confirm-order")
    public String confirmOrder(@ModelAttribute("orderedProducts") List<OrderedProduct> orderedProducts) {
        if (orderedProducts == null || orderedProducts.isEmpty()) {
            return "redirect:/";
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> user = userRepository.findByName(authentication.getName());

        Order order = new Order();
        order.setDeptId(user.get().getDepartment());
        double totalPrice = orderedProducts.stream().mapToDouble(item -> item.getItemPrice() * item.getAmount()).sum();
        order.setTotalPrice(totalPrice);
        order.setStatusId(1L);

        orderRepository.save(order);

        for (OrderedProduct orderedProduct : orderedProducts) {
            orderedProduct.setOrder(order);
            orderedProductRepository.save(orderedProduct);
        }

        return "redirect:/order-success";
    }

    @GetMapping("/order-success")
    public String orderSuccess() {
        return "/organization/order_success";
    }


    // ... other existing code ...

//    @PostMapping("/selectedProducts")
//    public String selectedProducts(@RequestParam List<Long> selectedProducts,
//                                   @RequestParam Map<String, String> quantities,
//                                   Model model,
//                                   Principal principal) {
//        if (pageDataManager.setSelectedProductsModel(selectedProducts, quantities, model)) {
//            return "redirect:/";
//        }
//        model.addAttribute("user", userRepository.findByName(principal.getName()).orElseThrow(() ->
//                new UsernameNotFoundException("User not found: " + principal.getName())));
//        return "organization/editProducts";
//    }
//    @GetMapping("/editProducts")
//    public String editProducts(Model model) {
//        OrderedProduct orderedProduct = new OrderedProduct();
//        orderedProduct.setItemPrice(100.0); // Пример значения
//        orderedProduct.setAmount(2L);       // Пример значения
//        model.addAttribute("orderedProduct", orderedProduct);
//        return "organization/editProducts";
//    }
//    @PostMapping("/confirmOrder")
//    public String confirmOrder(@RequestParam Map<String, String> orderedProducts) {
//        try {
//            pageDataManager.saveNewOrder(orderedProducts);
//            return "redirect:/";
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "error";
//        }
//    }

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
        User user = userRepository.findByName(principal.getName()).orElseThrow(() -> new UsernameNotFoundException("User not found: " + principal.getName()));
        pageDataManager.setEditUserModel(id, model, user);
        return "editUser";
    }


    private void getAvailableProductsModel(Model model, User user) {
        List<AvailableProducts> availableProducts = availableProductsRepository.findAll();
        model.addAttribute("availableProducts", availableProducts);
        model.addAttribute("user", user);
    }


    @PostMapping("/edituser")
    public String editUser(@RequestParam("id") Long id, @RequestParam("department") String department, @RequestParam("name") String name, @RequestParam("role") String role, @RequestParam("login") String login, @RequestParam("phone") String phone, @RequestParam("email") String email, @RequestParam("hired_at") LocalDate created_at) {

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