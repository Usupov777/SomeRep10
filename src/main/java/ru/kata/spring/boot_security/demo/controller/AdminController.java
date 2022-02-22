package ru.kata.spring.boot_security.demo.controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;


import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/")
    public String allUsers(Principal principal,Model model){
        User user = userService.findUserByEmail(principal.getName());
        List<User> users = userService.allUsers();
        model.addAttribute("userAuthorised", user);
        model.addAttribute("usersList", users);
        model.addAttribute("newUser", new User());
        Set<Role> roleSet= userService.allRoles();
        model.addAttribute("roleSet", roleSet);
        return "users";
    }
    @GetMapping("/addUser")
    public String showUserAddPage(Model model){
        model.addAttribute("user", new User());
        Set<Role> roleSet= userService.allRoles();
        model.addAttribute("roleSet", roleSet);
        return "addUser";
    }
    @PostMapping("/addUser")
    public String saveUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                           @RequestParam ("role_authorities") List<Integer> role_id){
        if (bindingResult.hasErrors())
            return "addUser";
        user.setRoles(userService.getSetOfRoles(role_id));
        userService.add(user);
        return "redirect:/admin/";
    }
    @GetMapping("/editUser/{id}")
    public String showUserEditPage(@PathVariable(value = "id") int id, Model model){
        User user = userService.getById(id);
        Set<Role> roleSet= userService.allRoles();
        model.addAttribute("user", user);
        model.addAttribute("roleSet", roleSet);
        return "editUser";
    }
    @PostMapping("/editUser/{id}")
    public String editUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                           @RequestParam (value = "role_authorities", required = false) List<Integer> role_id){
        if (bindingResult.hasErrors())
            return "editUser";
        if(role_id != null){
            user.setRoles(userService.getSetOfRoles(role_id));
        }
        userService.edit(user);
        return "redirect:/admin/";
    }
    @GetMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable(value = "id") int id,
                             Model model){
        User user = userService.getById(id);
        userService.delete(user);
        return "redirect:/admin/";
    }

}
