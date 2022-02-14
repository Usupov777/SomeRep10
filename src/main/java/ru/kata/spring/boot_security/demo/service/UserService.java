package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService{
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.userRepository=userRepository;
        this.roleRepository=roleRepository;
        this.bCryptPasswordEncoder=bCryptPasswordEncoder;
    }
    public List<User> allUsers(){
        return userRepository.findAll();
    }
    public boolean add(User user){
        User userFromDB = userRepository.findUserByUsername(user.getUsername());

        if (userFromDB != null) {
            return false;
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }
    public void delete(User user){
        userRepository.delete(user);
    }
    public void edit(User user){
        userRepository.saveAndFlush(user);
    }
    public User getById(int id){
        return userRepository.getById(id);
    }
    public User findUserByUsername(String username){
        return userRepository.findUserByUsername(username);
    }

    public Set<Role> allRoles(){
        return new HashSet<>(roleRepository.findAll());
    }

    public Set<Role> getSetOfRoles(List<Integer> role_id) {
        Set<Role> roles = new HashSet<>();
        for (Integer idOfRole : role_id) {
            roles.add(roleRepository.findRoleById(idOfRole));
        }
        return roles;
    }
}
