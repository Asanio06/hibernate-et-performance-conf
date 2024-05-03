package com.example.hibernate.external.model;

import java.util.Date;
import java.util.List;

public record MovieInformation(String name, String countryCode, Date releaseDate, List<MovieActorInformation> actors,
                               List<MovieRatingInformation> ratings) {
}


