package com.example.hibernate.mapper;

import com.example.hibernate.entity.Actor;
import com.example.hibernate.entity.Rating;
import com.example.hibernate.external.model.MovieActorInformation;
import com.example.hibernate.external.model.MovieRatingInformation;

public class RatingMapper {


    public static Rating fromMovieRatingInformation(MovieRatingInformation movieRatingInformation) {
        var rating = new Rating();

        rating.setNote(movieRatingInformation.note());
        rating.setComment(movieRatingInformation.comment());

        return rating;
    }


}
