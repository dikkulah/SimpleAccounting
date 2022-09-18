package com.dikkulah.accountservice.model;

import com.dikkulah.accountservice.model.enums.Role;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "users")
public class User extends AbstractEntity{
    @Column(updatable = false,unique = true)
    private String username;
    private String hashedPassword;

    @OneToMany(mappedBy = "user")
    private List<Account> accounts = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Exchange> exchanges =  new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<>();

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public Set<Role> getRoles() {
        return roles;
    }
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
    public String getHashedPassword() {
        return hashedPassword;
    }
    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return getId() != null && Objects.equals(getId(), user.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
