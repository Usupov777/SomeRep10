package ru.kata.spring.boot_security.demo.controller;

import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.model.UserDto;
import ru.kata.spring.boot_security.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/")
    public String allUsers(Model model){
        List<User> users = userService.allUsers();
        model.addAttribute("usersList", users);
        return "users";
    }
    @GetMapping("/addUser")
    public String showUserAddPage(Model model){
        UserDto userForm = new UserDto();
        model.addAttribute(userForm);
        return "addUser";
    }
    @PostMapping("/addUser")
    public String saveUser(Model model,
                           @ModelAttribute("userForm") UserDto userFormFromModel){
        String email = userFormFromModel.getEmail();
        String username = userFormFromModel.getUsername();
        String password = userFormFromModel.getPassword();

        if (username != null && username.length() > 0 && password != null && password.length() > 0 && email != null && email.length() > 0){
            userService.add(new User(email,username,password));
        }
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
    public String editUser(@ModelAttribute("user") User user,
                           @ModelAttribute("roleSet") HashSet<Role> roleSet,
                           Model model){
        user.getRoles().clear();
        user.setRoles(roleSet);
        System.out.println(roleSet);
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
