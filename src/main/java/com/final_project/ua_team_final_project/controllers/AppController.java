package com.final_project.ua_team_final_project.controllers;

import com.final_project.ua_team_final_project.models.User;
import com.final_project.ua_team_final_project.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AppController {

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @GetMapping("/")
    public String index(Model model) {

        User userAccount = new User();
        userAccount.setUser_id(123L);
        userAccount.setName("Admin");
        userAccount.setPassword_enc("yoyoyo");

        model.addAttribute("user", userAccount);

        return "index";
    }

    @GetMapping("/register")
    public String register() {

        return "register";
    }

    @PostMapping("/register")
    public String postRegister(@ModelAttribute User userAccount) {

        customUserDetailsService.createUserAccount(
                userAccount.getName(),
                userAccount.getPassword_enc()
        );

        return "redirect:/register";
    }

}