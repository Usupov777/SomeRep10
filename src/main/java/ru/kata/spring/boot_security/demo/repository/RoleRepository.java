package ru.kata.spring.boot_security.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;
import java.util.Set;


public interface RoleRepository extends JpaRepository<Role, Integer> {
    List<Role> findAll();
    Role findRoleByName(String role);
    Role findRoleById(int id);
}
