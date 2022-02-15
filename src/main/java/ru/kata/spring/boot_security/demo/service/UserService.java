package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Set;

public interface UserService extends UserDetailsService {
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
    List<User> allUsers();
    boolean add(User user);
    void delete(User user);
    void edit(User user);
    User getById(int id);
    User findUserByUsername(String username);
    Set<Role> allRoles();
    Set<Role> getSetOfRoles(List<Integer> role_id);
}
