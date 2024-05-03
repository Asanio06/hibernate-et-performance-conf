package com.example.hibernate.mapper;

import com.example.hibernate.entity.Movie;
import com.example.hibernate.external.model.MovieInformation;

public class MovieMapper {


    public static Movie fromMovieInformation(MovieInformation movieInformation) {
        var movie = new Movie();
        movie.setName(movieInformation.name());
        movie.setCountryCode(movieInformation.countryCode());
        movie.setReleaseDate(movieInformation.releaseDate());

        return movie;
    }


}
