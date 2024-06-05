package ru.kata.sping.boot_security_bootstrap.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.sping.boot_security_bootstrap.model.Role;
import ru.kata.sping.boot_security_bootstrap.model.User;
import ru.kata.sping.boot_security_bootstrap.services.UserService;
import ru.kata.sping.boot_security_bootstrap.services.UserServiceImpl;
import ru.kata.sping.boot_security_bootstrap.util.UserValidator;


import javax.validation.Valid;
import java.util.List;


@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final UserValidator userValidator;

    @Autowired
    public AdminController(UserServiceImpl userServiceImpl, UserValidator userValidator) {
        this.userService = userServiceImpl;
        this.userValidator = userValidator;
    }

    @GetMapping
    public String showUsers(ModelMap model) {
        List<Role> listRoles = userService.getAllRoles();
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("listRoles", listRoles);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.getUserByEmail(username);
        model.addAttribute("user", user);
        model.addAttribute("newUser", new User());
        return "users";
    }

    @GetMapping(value = "/addUser")
    public String addUser(Model model) {
        List<Role> listRoles = userService.getAllRoles();
        model.addAttribute("user", new User());
        model.addAttribute("listRoles", listRoles);
        return "users";
    }

    @PostMapping("/addUser")
    public String createUser(@ModelAttribute @Valid User user, BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "users";
        }
        userService.save(user);
        return "redirect:/admin";
    }

    @GetMapping(value = "/editUser")
    public String editUser(@RequestParam("id") Long id, Model model) {
        List<Role> listRoles = userService.getAllRoles();
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("listRoles", listRoles);
        return "users";
    }

    @PostMapping("/editUser")
    public String update(@RequestParam("id") Long id, @ModelAttribute("user") @Valid User user,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "users";
        }
        userService.update(id, user);
        return "redirect:/admin";
    }

    @GetMapping(value = "/deleteUser")
    public String deleteUser(@RequestParam(name = "id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

}
