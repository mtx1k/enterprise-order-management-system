package com.final_project.ua_team_final_project.controllers;

import com.final_project.ua_team_final_project.services.PageDataManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
public class AppController {

    private final PageDataManager pageDataManager;

    public AppController(PageDataManager pageDataManager) {
        this.pageDataManager = pageDataManager;
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
}