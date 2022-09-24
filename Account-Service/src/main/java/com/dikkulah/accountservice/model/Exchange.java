package com.dikkulah.accountservice.model;

import com.dikkulah.accountservice.model.enums.Currency;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Getter
@Setter
public class Exchange extends AbstractEntity{


    @Enumerated(EnumType.STRING)
    private Currency currencyFrom;

    @Enumerated(EnumType.STRING)
    private Currency currencyTo;

    private BigDecimal conversionMultiple;

    private BigDecimal quantity;

    private BigDecimal totalAmount;

    private String token;

    @ManyToOne
    @JoinColumn(name = "account_from_id")
    private Account accountFrom;

    @ManyToOne
    @JoinColumn(name = "account_to_id")
    private Account accountTo;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Exchange exchange = (Exchange) o;
        return getId() != null && Objects.equals(getId(), exchange.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
