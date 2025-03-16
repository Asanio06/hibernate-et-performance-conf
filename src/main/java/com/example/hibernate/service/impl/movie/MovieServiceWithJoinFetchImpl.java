package com.example.hibernate.service.impl.movie;

import com.example.hibernate.entity.Movie;
import com.example.hibernate.service.MovieService;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
@Profile("join_fetch")
public class MovieServiceWithJoinFetchImpl implements MovieService {

    private final EntityManager entityManager;


    public MovieServiceWithJoinFetchImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public List<Movie> getMovies(List<String> countryCodes) {
        var session = entityManager.unwrap(Session.class);

        if (countryCodes == null || countryCodes.isEmpty()) {
            return session
                    .createQuery("""
                               select m from Movie m
                               left join fetch m.ratings
                               left join fetch m.movieActors ma
                               left join fetch ma.actor
                            """, Movie.class)
                    .getResultList();
        }

        return session
                .createQuery("""
                           select m from Movie m
                           left join fetch m.ratings
                           left join fetch m.movieActors ma
                           left join fetch ma.actor
                           where m.countryCode in (:countryCodes)
                        """, Movie.class)
                .setParameter("countryCodes", countryCodes)
                .getResultList();
    }
}
