package com.example.hibernate.controller;


import com.example.hibernate.entity.Movie;
import com.example.hibernate.service.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {


    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Movie> getMovies(@RequestParam(required = false,name = "countryCode") List<String> countryCodes) {
        return movieService.getMovies(countryCodes);
    }
}
