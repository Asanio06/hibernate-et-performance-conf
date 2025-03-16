package com.example.hibernate.service.impl.movie;

import com.example.hibernate.entity.Movie;
import com.example.hibernate.service.MovieService;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Primary
@Profile("multiple_query")
public class MovieServiceWithMultipleQueryImpl implements MovieService {

    private final EntityManager entityManager;


    public MovieServiceWithMultipleQueryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    @Transactional(readOnly = true)
    public List<Movie> getMovies(List<String> countryCodes) {
        var session = entityManager.unwrap(Session.class);

        var movies = session
                .createQuery("""
                           select m from Movie m
                           left join fetch m.movieActors ma
                           left join fetch ma.actor
                           where m.countryCode in (:countryCodes)
                        """, Movie.class)
                .setParameter("countryCodes", countryCodes)
                .getResultList();

        session.createQuery("""
                        select m from Movie m
                        left join fetch m.ratings
                        where m in (:movies)
                        """)
                .setParameter("movies", movies)
                .getResultList();

        return movies;
    }
}
