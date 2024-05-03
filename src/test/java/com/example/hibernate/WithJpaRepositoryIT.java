package com.example.hibernate;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.time.Instant;


import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class WithJpaRepositoryIT extends BasicIT {


    @ParameterizedTest
    @ValueSource(ints = {1, 10, 100, 500, 1000, 3000})
    void movies_with_actors_and_ratings_fetching_with_two_queries_via_jpa_repository(int moviesNumber) {
        insertMovies(moviesNumber);


        Instant start = Instant.now();

        var movies = movieRepository.findAllWithActorsAndRatings((root, query, criteriaBuilder) -> criteriaBuilder.and());

        Instant finish = Instant.now();

        long timeElapsed = Duration.between(start, finish).toMillis();

        System.out.println("Nb de film = " + movies.size() + " Durée = " + timeElapsed + " ms");

        assertThat(movies).allSatisfy(movie -> {
            assertThat(movie.getActors()).hasSize(ACTORS_NUMBER_BY_MOVIE);
            assertThat(movie.getRatings()).hasSize(RATINGS_NUMBER_BY_MOVIE);
        });
    }


}
