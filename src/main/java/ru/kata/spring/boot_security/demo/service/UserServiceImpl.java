package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.userRepository=userRepository;
        this.roleRepository=roleRepository;
        this.bCryptPasswordEncoder=bCryptPasswordEncoder;
    }
    @Override
    public List<User> allUsers(){
        return userRepository.findAll();
    }
    @Override
    public boolean add(User user){
        User userFromDB = userRepository.findUserByEmail(user.getEmail());

        if (userFromDB != null) {
            return false;
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }
    @Override
    public void delete(User user){
        userRepository.delete(user);
    }
    @Override
    public void edit(User user){
        User userFromDB = userRepository.findUserByEmail(user.getEmail());

        if (!Objects.equals(userFromDB.getPassword(), user.getPassword())) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }
        userRepository.saveAndFlush(user);
    }
    @Override
    public User getById(int id){
        return userRepository.getById(id);
    }
    @Override
    public User findUserByEmail(String email){
        return userRepository.findUserByEmail(email);
    }
    @Override
    public Set<Role> allRoles(){
        return new HashSet<>(roleRepository.findAll());
    }
    @Override
    public Set<Role> getSetOfRoles(List<Integer> role_id) {
        Set<Role> roles = new HashSet<>();
        for (Integer idOfRole : role_id) {
            roles.add(roleRepository.findRoleById(idOfRole));
        }
        return roles;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(email);
    }
}
