package ru.kata.spring.boot_security.demo.model;

import lombok.Data;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "roles")
@Data
public class Role implements GrantedAuthority {
    @Id
    private int id;
    private String name;
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;
    public Role() {
    }

    public Role(int id) {
        this.id = id;
    }

    public Role(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public String getAuthority() {
        return getName();
    }
}
