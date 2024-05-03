package com.example.hibernate.mapper;

import com.example.hibernate.entity.Actor;
import com.example.hibernate.entity.Movie;
import com.example.hibernate.external.model.MovieActorInformation;
import com.example.hibernate.external.model.MovieInformation;

public class ActorMapper {


    public static Actor fromMovieActorInformation(MovieActorInformation movieActorInformation) {
        var actor = new Actor();
        actor.setFirstname(movieActorInformation.firstname());
        actor.setLastname(movieActorInformation.lastname());

        return actor;
    }


}
