package com.final_project.ua_team_final_project.controllers;

import com.final_project.ua_team_final_project.models.Role;
import com.final_project.ua_team_final_project.models.User;
import com.final_project.ua_team_final_project.repositories.DepartmentRepository;
import com.final_project.ua_team_final_project.repositories.RoleRepository;
import com.final_project.ua_team_final_project.repositories.UserRepository;
import com.final_project.ua_team_final_project.services.PageDataManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Controller
public class AppController {

    private final PageDataManager pageDataManager;
    private final UserRepository userRepository;
    private final DepartmentRepository deptRepository;
    private final RoleRepository roleRepository;

    public AppController(PageDataManager pageDataManager, UserRepository userRepository,
                         DepartmentRepository deptRepository, RoleRepository roleRepository) {
        this.pageDataManager = pageDataManager;
        this.userRepository = userRepository;
        this.deptRepository = deptRepository;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/")
    public String index(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            pageDataManager.setAdminModel(model);
            return "/adminpage";
        } else if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER"))) {
            return "/userpage";
        } else {
            return "redirect:/login";
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