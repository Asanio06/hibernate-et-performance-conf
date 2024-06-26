package com.example.hibernate;


import com.example.hibernate.entity.MovieActor;
import com.example.hibernate.external.client.MovieInformationClient;
import com.example.hibernate.mapper.ActorMapper;
import com.example.hibernate.mapper.MovieMapper;
import com.example.hibernate.mapper.RatingMapper;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;


import static com.example.hibernate.external.client.MovieInformationClient.getFakeMoviesInformation;


@SpringBootTest
@AutoConfigureMockMvc
public abstract class BasicIT {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    protected MockMvc mvc;

    @MockBean
    protected MovieInformationClient movieInformationClient;


    protected void insertFakeMovies(int moviesNumber) {
        Session session = sessionFactory.openSession();
        session.setFetchBatchSize(30);
        Transaction tx = null;
        var moviesInformations = getFakeMoviesInformation(moviesNumber);
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
            System.out.println("\u001B[2J");
            session.close();
        }
    }


//    @AfterEach
//    void clean() {
//        Session session = sessionFactory.openSession();
//        Transaction tx = null;
//        try {
//            tx = session.beginTransaction();
//            session.createMutationQuery("DELETE from Rating").executeUpdate();
//            session.createMutationQuery("DELETE from MovieActor").executeUpdate();
//            session.createMutationQuery("DELETE from Movie").executeUpdate();
//            session.createMutationQuery("DELETE from Actor").executeUpdate();
//            tx.commit();
//        } catch (RuntimeException e) {
//            if (tx != null) tx.rollback();
//            throw e; // or display error message
//        } finally {
//            session.close();
//        }
//    }
}

