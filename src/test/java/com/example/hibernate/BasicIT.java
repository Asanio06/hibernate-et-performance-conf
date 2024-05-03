package com.example.hibernate;

import com.example.hibernate.entity.Actor;
import com.example.hibernate.entity.Movie;
import com.example.hibernate.entity.Rating;
import com.example.hibernate.repository.MovieRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.instancio.Instancio;
import org.instancio.Model;
import org.instancio.Select;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
abstract class BasicIT {


    @Autowired
    protected SessionFactory sessionFactory;

    @Autowired
    protected MovieRepository movieRepository;

    private static Model<Movie> movieModel = Instancio.of(Movie.class)
            .ignore(Select.field(Movie::getId))
            .ignore(Select.field(Movie::getActors))
            .ignore(Select.field(Movie::getRatings))
            .toModel();


    private static Model<Actor> actorModel = Instancio.of(Actor.class)
            .ignore(Select.field(Actor::getId))
            .toModel();


    private static Model<Rating> ratingModel = Instancio.of(Rating.class)
            .ignore(Select.field(Rating::getId))
            .ignore(Select.field(Rating::getMovie))
            .toModel();

    protected final static int ACTORS_NUMBER_BY_MOVIE = 2;
    protected final static int RATINGS_NUMBER_BY_MOVIE = 2;




    protected void insertMovies(int moviesNumber) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();

            Instancio.stream(movieModel)
                    .limit(moviesNumber)
                    .forEach(
                            movie -> {

                                session.persist(movie);

                                Instancio.stream(actorModel)
                                        .limit(ACTORS_NUMBER_BY_MOVIE)
                                        .forEach(actor -> {
                                            movie.addActor(actor);
                                            session.persist(actor);
                                        });

                                Instancio.stream(ratingModel)
                                        .limit(RATINGS_NUMBER_BY_MOVIE)
                                        .forEach(rating -> {
                                            movie.addRating(rating);
                                            session.persist(rating);
                                        });
                            }
                    );
            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null) tx.rollback();
            throw e; // or display error message
        } finally {
            session.close();
        }
    }

    @AfterEach
    void clean() {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.createMutationQuery("DELETE from Rating").executeUpdate();
            session.createMutationQuery("DELETE from Movie").executeUpdate();
            session.createMutationQuery("DELETE from Actor").executeUpdate();
            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null) tx.rollback();
            throw e; // or display error message
        } finally {
            session.close();
        }
    }
}

