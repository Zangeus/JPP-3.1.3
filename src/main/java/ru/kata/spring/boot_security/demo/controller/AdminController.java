package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
//import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String adminPage(Model model) {
        model.addAttribute("allUsers", userService.getAllUsers());
        return "admin_page/all-users";
    }

    @GetMapping("/createNewUser")
    public String createNewUser(Model model) {
        model.addAttribute("user", new User());
//        model.addAttribute("roles", new Role());
        return "admin_page/user-create";
    }

    @PostMapping("/saveUser")
    public String addUser(@ModelAttribute("user") User user) {
        userService.createUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/editUser")
    public String editUser(@RequestParam("id") int id, Model model) {
        User user = userService.getUser(id);
        model.addAttribute("user", user);
        return "admin_page/user-update";
    }

    @PatchMapping("/updateUser")
    public String updateUser(@ModelAttribute("user") User user) {
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/deleteUser")
    public String deleteUser(@RequestParam("id") int id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
