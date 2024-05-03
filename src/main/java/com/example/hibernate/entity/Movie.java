package com.example.hibernate.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.*;

@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movie_generator")
    @SequenceGenerator(name = "movie_generator", sequenceName = "movie_seq", allocationSize = 50)
    private Long id;

    @Column(name = "name", nullable = false, length = 50)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String name;

    @Column
    @JdbcTypeCode(SqlTypes.TIMESTAMP)
    private Date releaseDate;

    @Column
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String countryCode;

    @OneToMany(mappedBy = "movie", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Rating> ratings;

    @OneToMany(mappedBy = "movie", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<MovieActor> movieActors;

    public Movie() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void addMovieActor(MovieActor movieActor) {
        if (this.movieActors == null) {
            this.movieActors = new ArrayList<>();
        }
        this.movieActors.add(movieActor);
    }

    public void removeMovieActor(MovieActor movieActor) {
        this.movieActors.remove(movieActor);
    }

    public List<MovieActor> getMovieActors() {
        return movieActors;
    }

    public void setMovieActors(List<MovieActor> movieActors) {
        this.movieActors = movieActors;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    public void addRating(Rating rating) {
        if (this.ratings == null) {
            this.ratings = new ArrayList<>();
        }
        this.ratings.add(rating);
        rating.setMovie(this);
    }

    public void removeRating(Rating rating) {
        this.ratings.remove(rating);
        rating.setMovie(null);
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
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
                ", releaseDate=" + releaseDate +
                ", countryCode='" + countryCode + '\'' +
                '}';
    }
}
