package com.dikkulah.accountservice.model;

import org.hibernate.annotations.Type;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Objects;
import java.util.UUID;

@MappedSuperclass
public abstract class AbstractEntity {

    @Id
    @GeneratedValue
    @Type(type = "uuid-char")
    private UUID id ;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractEntity)) return false;
        AbstractEntity that = (AbstractEntity) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
