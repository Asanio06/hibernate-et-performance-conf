package com.example.hibernate.repository;

import com.example.hibernate.entity.Movie;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long>, JpaSpecificationExecutor<Movie> {
    @Query("""
            select m from Movie m
            join fetch m.ratings
            """)
    List<Movie> findAllWithRatings();

    @Query("""
            select m from Movie m
            join fetch m.actors
            where m in (:movies)
            """)
    List<Movie> findAllWithActors(List<Movie> movies);

    @Transactional
    default List<Movie> findAllWithActorsAndRatings() {
        var movies = findAllWithRatings();
        return movies.isEmpty() ? movies : findAllWithActors(movies);
    }


    @Override
    @EntityGraph(attributePaths = {"ratings"})
    List<Movie> findAll(Specification<Movie> specification);

    @Transactional
    default List<Movie> findAllWithActorsAndRatings(Specification<Movie> specification) {
        var movies = findAll(specification);
        return movies.isEmpty() ? movies : findAllWithActors(movies);
    }


}
