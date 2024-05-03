package com.example.hibernate.external.client;


import com.example.hibernate.external.model.MovieActorInformation;
import com.example.hibernate.external.model.MovieInformation;
import com.example.hibernate.external.model.MovieRatingInformation;
import org.instancio.Instancio;
import org.instancio.Model;
import org.instancio.Select;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieInformationClient {
    protected final static int ACTORS_NUMBER_BY_MOVIE = 10;
    protected final static int RATINGS_NUMBER_BY_MOVIE = 10;


    public static Model<MovieActorInformation> actorModel = Instancio.of(MovieActorInformation.class)
            .toModel();


    public static Model<MovieRatingInformation> ratingModel = Instancio.of(MovieRatingInformation.class)
            .generate(Select.field(MovieRatingInformation::note), generators -> generators.ints().range(8, 10))
            .generate(Select.field(MovieRatingInformation::comment), generators -> generators.oneOf("So good", "Awesome", "Good job"))
            .toModel();

    public static Model<MovieInformation> movieModel = Instancio.of(MovieInformation.class)
            .supply(Select.field(MovieInformation::ratings), () -> Instancio.ofList(ratingModel).size(RATINGS_NUMBER_BY_MOVIE).create())
            .supply(Select.field(MovieInformation::actors), () -> Instancio.ofList(actorModel).size(ACTORS_NUMBER_BY_MOVIE).create())
            .generate(Select.field(MovieInformation::countryCode), generators -> generators.oneOf("FR", "IT", "ES", "US", "UK", "CI", "BE", "CH"))
            .toModel();

    public static List<MovieInformation> getFakeMoviesInformation(int movieNumber) {
        return Instancio.ofList(movieModel).size(movieNumber).create();
    }

    public MovieInformationClient() {

    }

    public List<MovieInformation> getMoviesInformations() {
        return getFakeMoviesInformation(100);
    }
}
