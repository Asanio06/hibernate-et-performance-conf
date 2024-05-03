package com.example.hibernate.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;
import java.util.Objects;

@Entity
public class MovieActor {

    @Embeddable
    static class MovieActorId implements Serializable {

        private Long movieId;
        private Long actorId;

        public MovieActorId() {
        }

        public MovieActorId(Long movieId, Long actorId) {
            this();
            this.movieId = movieId;
            this.actorId = actorId;
        }

        public Long getMovieId() {
            return movieId;
        }

        public void setMovieId(Long movieId) {
            this.movieId = movieId;
        }

        public Long getActorId() {
            return actorId;
        }

        public void setActorId(Long actorId) {
            this.actorId = actorId;
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            MovieActorId that = (MovieActorId) o;
            return Objects.equals(movieId, that.movieId) && Objects.equals(actorId, that.actorId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(movieId, actorId);
        }

        @Override
        public String toString() {
            return "MovieActorId{" +
                    "movieId=" + movieId +
                    ", actorId=" + actorId +
                    '}';
        }
    }

    @EmbeddedId
    private MovieActorId id = new MovieActorId();

    @ManyToOne
    @MapsId("movieId")
    @JsonBackReference
    private Movie movie;

    @ManyToOne
    @MapsId("actorId")
    @JsonBackReference
    private Actor actor;

    @Column
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String characterName;

    public MovieActorId getId() {
        return id;
    }

    public void setId(MovieActorId id) {
        this.id = id;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieActor that = (MovieActor) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "MovieActor{" +
                "id=" + id +
                ", movie=" + movie +
                ", actor=" + actor +
                ", characterName='" + characterName + '\'' +
                '}';
    }
}



