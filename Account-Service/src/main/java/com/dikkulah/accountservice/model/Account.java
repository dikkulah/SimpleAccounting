package com.dikkulah.accountservice.model;

import com.dikkulah.accountservice.model.enums.Currency;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "accounts")
public class Account extends AbstractEntity{


    @Enumerated(EnumType.STRING)
    @Column(updatable = false)
    private Currency currency;
    private BigDecimal amount;

    @OneToMany(mappedBy = "account")
    private List<Activity> activities = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false,updatable = false)
    private User user;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Account account = (Account) o;
        return getId() != null && Objects.equals(getId(), account.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
