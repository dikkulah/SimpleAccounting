package com.dikkulah.accountservice.model;

import com.dikkulah.accountservice.model.enums.ActivityType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "activities")
public class Activity extends AbstractEntity{

    private LocalDateTime time;
    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    private ActivityType activityType;
    private String description;

    @ManyToOne
    @JoinColumn(name = "account_id",nullable = false,updatable = false)
    private Account account;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Activity activity = (Activity) o;
        return getId() != null && Objects.equals(getId(), activity.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
