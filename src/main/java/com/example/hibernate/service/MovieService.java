package com.example.hibernate.service;


import com.example.hibernate.entity.Movie;

import java.util.List;

public interface MovieService {


    List<Movie> getMovies(List<String> countryCodes);
}
