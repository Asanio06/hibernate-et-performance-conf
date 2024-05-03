package com.example.hibernate.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Objects;
import java.util.UUID;

@Entity
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "rating_generator")
    @SequenceGenerator(name="rating_generator", sequenceName = "rating_seq",allocationSize = 50)
    private Long id;

    @Column
    @JdbcTypeCode(SqlTypes.INTEGER)
    private Integer note;
    @Column
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String comment;

    @ManyToOne(optional = false)
    @JsonBackReference
    private Movie movie;


    public Rating() {

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Integer getNote() {
        return note;
    }

    public void setNote(Integer note) {
        this.note = note;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rating rating = (Rating) o;
        return Objects.equals(id, rating.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Rating{" +
                "id=" + id +
                ", note='" + note + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
