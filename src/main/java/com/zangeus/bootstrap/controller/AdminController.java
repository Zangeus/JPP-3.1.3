package com.zangeus.bootstrap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
//import ru.kata.spring.boot_security.demo.model.Role;
import com.zangeus.bootstrap.entities.User;
import com.zangeus.bootstrap.service.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String adminPage(Model model, Principal principal) {
        model.addAttribute("allUsers", userService.getAllUsers());
        try {
            model.addAttribute("user",
                    userService.getUser(
                            userService.findByEmail(principal.getName()).getId()));
        } catch (Exception e) {
            return "redirect:/";
        }
        return "admin";
    }

    @PostMapping("/createUser")
    public String createUser(@ModelAttribute("user") User user) {
        user.setId(new User().getId());
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @PatchMapping("/updateUser")
    public String updateUser(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/deleteUser")
    public String deleteUser(@ModelAttribute("id") int id) {
        try {
            userService.deleteUserAndHisTokensById(id);
        } catch (NullPointerException e) {
            System.out.println("User with current id doesn't exists: " + id);
        }
        return "redirect:/admin";
    }
}
