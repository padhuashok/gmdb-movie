package com.galvanize.gmdbmovie.controller;

import com.galvanize.gmdbmovie.domain.Actor;
import com.galvanize.gmdbmovie.domain.Movie;
import com.galvanize.gmdbmovie.repository.MovieRepository;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
import java.util.Optional;

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

    @RequestMapping(method = RequestMethod.GET,value="/byTitle")
    public Movie getMovieByTitle(@RequestParam String title){
        Optional<Movie> movie = this.movieRepository.findByTitle(title);
        if( !movie.isPresent()) {
            throw new NoSuchElementException("Movie Does not Exist");
        }
        return movie.get();
    }

    @PatchMapping(value = "/rating/{rating}/id/{id}")
    public Movie submitRating(@PathVariable("rating") Integer rating,@PathVariable Long id){
        Optional<Movie> existingMovie = this.movieRepository.findById(id);
        if(existingMovie.isPresent()){
            existingMovie.get().setRating(rating);
            this.movieRepository.save(existingMovie.get());
        }else{
            throw new NoSuchElementException("Movie Does not Exist");
        }
        return existingMovie.get();
    }

}
