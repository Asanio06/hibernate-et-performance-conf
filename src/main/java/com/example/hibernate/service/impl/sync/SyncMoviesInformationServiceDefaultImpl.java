package com.example.hibernate.service.impl.sync;


import com.example.hibernate.entity.MovieActor;
import com.example.hibernate.external.client.MovieInformationClient;
import com.example.hibernate.external.model.MovieInformation;
import com.example.hibernate.mapper.ActorMapper;
import com.example.hibernate.mapper.MovieMapper;
import com.example.hibernate.mapper.RatingMapper;
import com.example.hibernate.service.SyncMoviesInformationService;
import jakarta.persistence.FlushModeType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SyncMoviesInformationServiceDefaultImpl implements SyncMoviesInformationService {

    private final MovieInformationClient movieInformationClient;
    private final SessionFactory sessionFactory;

    public SyncMoviesInformationServiceDefaultImpl(MovieInformationClient movieInformationClient, SessionFactory sessionFactory) {
        this.movieInformationClient = movieInformationClient;
        this.sessionFactory = sessionFactory;
    }


    @Override
    public void syncMovies() {
        List<MovieInformation> moviesInformations = movieInformationClient.getMoviesInformations();

        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();

            moviesInformations.forEach(movieInformation -> {
                var movie = MovieMapper.fromMovieInformation(movieInformation);
                session.persist(movie);

                movieInformation.actors().forEach(movieActorInformation -> {
                    var actor = ActorMapper.fromMovieActorInformation(movieActorInformation);
                    session.persist(actor);
                    var movieActor = new MovieActor();
                    movieActor.setMovie(movie);
                    movieActor.setActor(actor);
                    movieActor.setCharacterName(movieActorInformation.characterName());
                    session.persist(movieActor);
                    movie.addMovieActor(movieActor);
                });

                movieInformation.ratings().forEach(movieRatingInformation -> {
                    var rating = RatingMapper.fromMovieRatingInformation(movieRatingInformation);
                    rating.setMovie(movie);
                    session.persist(rating);
                    movie.addRating(rating);
                });

            });

            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null) tx.rollback();
            throw e; // or display error message
        } finally {
            session.close();
        }

    }


}
