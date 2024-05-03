package com.example.hibernate;


import com.example.hibernate.entity.Movie;
import org.hibernate.Hibernate;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Duration;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

public class WithoutJpaRepositoryIT extends BasicIT {
    @ParameterizedTest
    @ValueSource(ints = {1, 10, 100, 500, 1000, 3000})
    void movies_without_relations(int moviesNumber) {

        var session = sessionFactory.openSession();
        insertMovies(moviesNumber);

        Instant start = Instant.now();
        var movies = session.createQuery("""
                select m from Movie m
                """, Movie.class).getResultList();
        Instant finish = Instant.now();

        long timeElapsed = Duration.between(start, finish).toMillis();

        System.out.println("Nb de film = " + movies.size() + " Durée = " + timeElapsed + " ms");
        session.close();
    }


    @ParameterizedTest
    @ValueSource(ints = {1, 10, 100, 500, 1000, 3000})
    void movies_with_fetch_join_on_actors(int moviesNumber) {

        var session = sessionFactory.openSession();

        insertMovies(moviesNumber);

        Instant start = Instant.now();
        var movies = session.createQuery("""
                select m from Movie m 
                    join fetch m.actors 
                """, Movie.class).getResultList();
        Instant finish = Instant.now();

        long timeElapsed = Duration.between(start, finish).toMillis();

        System.out.println("Nb de film = " + movies.size() + " Durée = " + timeElapsed + " ms");
        session.close();

        assertThat(movies).allSatisfy(movie -> {
            assertThat(movie.getActors()).hasSize(ACTORS_NUMBER_BY_MOVIE);
        });
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 10, 100, 500, 1000, 3000})
    void movies_with_fetch_join_on_actors_and_ratings(int moviesNumber) {
        var session = sessionFactory.openSession();

        insertMovies(moviesNumber);

        Instant start = Instant.now();
        var movies = session.createQuery("""
                select m from Movie m 
                    join fetch m.actors 
                    join fetch m.ratings 
                """, Movie.class).getResultList();
        Instant finish = Instant.now();

        long timeElapsed = Duration.between(start, finish).toMillis();

        System.out.println("Nb de film = " + movies.size() + " Durée = " + timeElapsed + " ms");
        session.close();

        assertThat(movies).allSatisfy(movie -> {
            assertThat(movie.getActors()).hasSize(ACTORS_NUMBER_BY_MOVIE);
            assertThat(movie.getRatings()).hasSize(RATINGS_NUMBER_BY_MOVIE);
        });

    }

    @ParameterizedTest
    @ValueSource(ints = {1, 10, 100, 500, 1000, 3000})
    void movies_withe_actors_and_ratings_fetching_with_two_queries(int moviesNumber) {

        insertMovies(moviesNumber);

        var session = sessionFactory.openSession();

        Instant start = Instant.now();

        var movies = session.createQuery("""
                select m from Movie m 
                    join fetch m.actors 
                """, Movie.class).getResultList();

        session.createQuery("""
                        select m from Movie m
                        join fetch m.ratings
                        where m in :movies
                        """, Movie.class)
                .setParameter("movies", movies)
                .getResultList();

        Instant finish = Instant.now();

        long timeElapsed = Duration.between(start, finish).toMillis();

        System.out.println("Nb de film = " + movies.size() + " Durée = " + timeElapsed + " ms");
        session.close();

        assertThat(movies).allSatisfy(movie -> {
            assertThat(movie.getActors()).hasSize(ACTORS_NUMBER_BY_MOVIE);
            assertThat(movie.getRatings()).hasSize(RATINGS_NUMBER_BY_MOVIE);
        });
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 10, 100, 500, 1000, 3000})
    void one_movie_with_join_fetch_on_actors_and_subselect_on_rating(int moviesNumber) {
        insertMovies(moviesNumber);

        var session = sessionFactory.openSession();
        Instant start = Instant.now();


        var movies = session.createQuery("""
                select m from Movie m 
                    join fetch m.actors 
                """, Movie.class).getResultList();

        Hibernate.initialize(movies.getFirst().getRatings());

        Instant finish = Instant.now();

        long timeElapsed = Duration.between(start, finish).toMillis();

        System.out.println("Nb de film = " + movies.size() + " Durée = " + timeElapsed + " ms");

        session.close();

        assertThat(movies).allSatisfy(movie -> {
            assertThat(movie.getActors()).hasSize(ACTORS_NUMBER_BY_MOVIE);
            assertThat(movie.getRatings()).hasSize(RATINGS_NUMBER_BY_MOVIE);
        });
    }

}
