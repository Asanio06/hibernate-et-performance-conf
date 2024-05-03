package com.example.hibernate.service.impl.sync;


import com.example.hibernate.entity.MovieActor;
import com.example.hibernate.external.client.MovieInformationClient;
import com.example.hibernate.external.model.MovieInformation;
import com.example.hibernate.mapper.ActorMapper;
import com.example.hibernate.mapper.MovieMapper;
import com.example.hibernate.mapper.RatingMapper;
import com.example.hibernate.service.SyncMoviesInformationService;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.Transaction;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Primary
@Profile("stateless_session")
public class SyncMoviesInformationServiceWithStatelessSessionImpl implements SyncMoviesInformationService {

    private final MovieInformationClient movieInformationClient;
    private final SessionFactory sessionFactory;

    public SyncMoviesInformationServiceWithStatelessSessionImpl(MovieInformationClient movieInformationClient, SessionFactory sessionFactory) {
        this.movieInformationClient = movieInformationClient;
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void syncMovies() {
        List<MovieInformation> moviesInformations = movieInformationClient.getMoviesInformations();

        StatelessSession session = sessionFactory.withStatelessOptions().openStatelessSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();

            var actorsToAdd = new ArrayList<>();

            var actorsMoviesToAdd = new ArrayList<>();

            var moviesRatingToAdd = new ArrayList<>();

            moviesInformations.stream()
                    .forEach(movieInformation -> {
                        var movie = MovieMapper.fromMovieInformation(movieInformation);
                        session.insert(movie);

                        movieInformation.actors().forEach(movieActorInformation -> {
                            var actor = ActorMapper.fromMovieActorInformation(movieActorInformation);
                            actorsToAdd.add(actor);

                            var movieActor = new MovieActor();
                            movieActor.setMovie(movie);
                            movieActor.setActor(actor);
                            movieActor.setCharacterName(movieActorInformation.characterName());
                            actorsMoviesToAdd.add(movieActor);
                        });

                        movieInformation.ratings().forEach(movieRatingInformation -> {
                            var rating = RatingMapper.fromMovieRatingInformation(movieRatingInformation);
                            rating.setMovie(movie);

                            moviesRatingToAdd.add(rating);
                        });

                    });

            actorsToAdd.forEach(session::insert);

            actorsMoviesToAdd.forEach(session::insert);

            moviesRatingToAdd.forEach(session::insert);

            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null) tx.rollback();
            throw e; // or display error message
        } finally {
            session.close();
        }

    }


}

