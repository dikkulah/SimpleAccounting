package com.dikkulah.accountservice.model;

import com.dikkulah.accountservice.model.enums.Role;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User extends AbstractEntity{
    @Column(updatable = false,unique = true)
    private String username;
    private String hashedPassword;

    @OneToMany(mappedBy = "user")
    private List<Account> accounts = new ArrayList<>();


    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<>();


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
