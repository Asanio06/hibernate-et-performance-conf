package com.example.hibernate.entity;


import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Objects;
import java.util.UUID;

@Entity
public class Actor {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "actor_generator")
    @SequenceGenerator(name = "actor_generator", sequenceName = "actor_seq", allocationSize = 50)
    private Long id;

    @Column
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String firstname;
    @Column
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String lastname;

    private Actor(Builder builder) {
        setFirstname(builder.firstname);
        setLastname(builder.lastname);
    }

    public Actor() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Actor actor = (Actor) o;
        return Objects.equals(id, actor.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Actor{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                '}';
    }

    public static final class Builder {
        private String firstname;
        private String lastname;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder withFirstname(String val) {
            firstname = val;
            return this;
        }

        public Builder withLastname(String val) {
            lastname = val;
            return this;
        }

        public Actor build() {
            return new Actor(this);
        }
    }
}
