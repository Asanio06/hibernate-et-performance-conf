package com.example.hibernate.entity;


import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
public class Movie {
    @Id
    @Column(name = "id", nullable = false,columnDefinition = "varchar(36)")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name", nullable = false, length = 50)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String name;


    @OneToMany(mappedBy = "movie", cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT)
    private Set<Rating> ratings;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "movie_actors",
            joinColumns = {
                    @JoinColumn(
                            name = "movie_id",
                            referencedColumnName = "id",
                            columnDefinition = "varchar(36)"
                    )
            },
            inverseJoinColumns = {
                    @JoinColumn(
                            name = "actor_id",
                            referencedColumnName = "id",
                            columnDefinition = "varchar(36)"
                    )
            },
            uniqueConstraints = @UniqueConstraint(columnNames = {"movie_id", "actor_id"})
    )
    private Set<Actor> actors;



    public Movie() {

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Set<Actor> getActors() {
        return actors;
    }

    public void addActor(Actor actor) {
        if (this.actors == null) {
            this.actors = new HashSet<>();
        }
        this.actors.add(actor);
    }

    public void removeActor(Actor actor) {
        this.actors.remove(actor);
    }

    public void setActors(Set<Actor> actors) {
        this.actors = actors;
    }

    public Set<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(Set<Rating> ratings) {
        this.ratings = ratings;
    }

    public void addRating(Rating rating) {
        if (this.ratings == null) {
            this.ratings = new HashSet<>();
        }
        this.ratings.add(rating);
        rating.setMovie(this);
    }

    public void removeRating(Rating rating) {
        this.ratings.remove(rating);
        rating.setMovie(null);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return Objects.equals(id, movie.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

}
