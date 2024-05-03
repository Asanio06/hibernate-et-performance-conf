package com.example.hibernate.service.impl.movie;

import com.example.hibernate.entity.Movie;
import com.example.hibernate.service.MovieService;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceDefaultImpl implements MovieService {

    private final EntityManager entityManager;

    public MovieServiceDefaultImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Movie> getMovies(List<String> countryCodes) {
        var session = entityManager.unwrap(Session.class);

        if (countryCodes == null || countryCodes.isEmpty()) {
            return session
                    .createQuery("from Movie m", Movie.class)
                    .getResultList();
        }

        return session
                .createQuery("""
                           select m from Movie m
                           where m.countryCode in (:countryCodes)
                        """, Movie.class)
                .setParameter("countryCodes", countryCodes)
                .getResultList();
    }
}
