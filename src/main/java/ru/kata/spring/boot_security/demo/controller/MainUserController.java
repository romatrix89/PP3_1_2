package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;


@Controller
public class MainUserController {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/user")
    public String userList(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByName(authentication.getName());
        model.addAttribute("users", user);
        return "user";
    }

    @GetMapping("/admin")
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin";
    }

    @GetMapping("/admin/addNewUser")
    public String addNewUsers(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("allRoles", userService.getAllRoles());
        return "new-user-data";
    }


    @PostMapping("/admin/saveNewUser")
    public String saveNewUsers(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/admin/toEditUser")
    public String toEditUser(Model model, Long id) {
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("allRoles", userService.getAllRoles());
        return "edit-user-data";
    }

    @PatchMapping("/admin/editUser")
    public String editUser(User user) {
        userService.editUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/admin/deleteUser")
    public String deleteUserById(Long id) {
        userService.deleteUserById(id);
        return "redirect:/admin";
    }

}
