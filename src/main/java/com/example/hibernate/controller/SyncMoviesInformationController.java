package com.example.hibernate.controller;


import com.example.hibernate.service.SyncMoviesInformationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sync")
public class SyncMoviesInformationController {

    private final SyncMoviesInformationService syncMoviesInformationService;

    public SyncMoviesInformationController(SyncMoviesInformationService syncMoviesInformationService) {
        this.syncMoviesInformationService = syncMoviesInformationService;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void syncMovies() {
        syncMoviesInformationService.syncMovies();
    }

}
