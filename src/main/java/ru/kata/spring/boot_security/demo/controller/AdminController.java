package ru.kata.spring.boot_security.demo.controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;


import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private UserServiceImpl userServiceImpl;

    @Autowired
    public void setUserService(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }


    @GetMapping("/")
    public String allUsers(Model model){
        List<User> users = userServiceImpl.allUsers();
        model.addAttribute("usersList", users);
        return "users";
    }
    @GetMapping("/addUser")
    public String showUserAddPage(Model model){
        model.addAttribute("user", new User());
        Set<Role> roleSet= userServiceImpl.allRoles();
        model.addAttribute("roleSet", roleSet);
        return "addUser";
    }
    @PostMapping("/addUser")
    public String saveUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                           @RequestParam ("role_authorities") List<Integer> role_id){
        if (bindingResult.hasErrors())
            return "addUser";
        user.setRoles(userServiceImpl.getSetOfRoles(role_id));
        userServiceImpl.add(user);
        return "redirect:/admin/";
    }
    @GetMapping("/editUser/{id}")
    public String showUserEditPage(@PathVariable(value = "id") int id, Model model){
        User user = userServiceImpl.getById(id);
        Set<Role> roleSet= userServiceImpl.allRoles();
        model.addAttribute("user", user);
        model.addAttribute("roleSet", roleSet);
        return "editUser";
    }
    @PostMapping("/editUser/{id}")
    public String editUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                           @RequestParam ("role_authorities") List<Integer> role_id){
        if (bindingResult.hasErrors())
            return "editUser";
        user.setRoles(userServiceImpl.getSetOfRoles(role_id));
        userServiceImpl.edit(user);
        return "redirect:/admin/";
    }
    @GetMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable(value = "id") int id,
                             Model model){
        User user = userServiceImpl.getById(id);
        userServiceImpl.delete(user);
        return "redirect:/admin/";
    }

}
