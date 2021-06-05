package com.galvanize.gmdbmovie.controller;

import com.galvanize.gmdbmovie.domain.Actor;
import com.galvanize.gmdbmovie.domain.Movie;
import com.galvanize.gmdbmovie.repository.MovieRepository;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieRepository movieRepository;

    public MovieController(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @RequestMapping(method = RequestMethod.GET,value="/")
    public Iterable<Movie> getAllMovies(){
        return this.movieRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.POST,value = "/add")
    public Movie addMovie(@RequestBody Movie movie){
        return this.movieRepository.save(movie);
    }
}
