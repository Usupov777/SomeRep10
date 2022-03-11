package ru.kata.spring.boot_security.demo.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/admin")
public class UserRestController {
    @Autowired
    private UserService userService;


    @GetMapping("/role/")
    public ResponseEntity<Set<Role>> getRoles(){
        Set<Role> roles = userService.allRoles();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }


    @GetMapping("/")
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userService.allUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/getOne/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Integer id) {
        User user = userService.getById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<User> addUser(@RequestBody @Valid User user){
        userService.add(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PutMapping("/")
    public ResponseEntity<User> editUser(@RequestBody @Valid User user)
    {
        userService.edit(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable("id") Integer id){
        userService.delete(userService.getById(id));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
