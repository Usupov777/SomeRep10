package ru.kata.spring.boot_security.demo.model;

import lombok.Data;

import java.util.Set;

@Data
public class UserDto {
    private String email;
    private String username;
    private String password;
    private Set<Role> roles;
}
