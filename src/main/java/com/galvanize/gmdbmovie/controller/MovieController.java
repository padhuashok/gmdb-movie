package com.galvanize.gmdbmovie.controller;

import com.galvanize.gmdbmovie.domain.Movie;
import com.galvanize.gmdbmovie.domain.Rating;
import com.galvanize.gmdbmovie.dto.RatingDto;
import com.galvanize.gmdbmovie.repository.MovieRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public Movie submitRating(@RequestBody() Rating rating, @PathVariable Long id){
        Optional<Movie> existingMovie = this.movieRepository.findById(id);
        if(existingMovie.isPresent()){
            List<Rating> ratings  = existingMovie.get().getRating();
            ratings.add(rating);
            this.movieRepository.save(existingMovie.get());
        }else{
            throw new NoSuchElementException("Movie Does not Exist");
        }
        return existingMovie.get();
    }
    
    @GetMapping(value = "/avgrating/{id}")
    public Movie getAverageRating(@PathVariable Long id){
        Optional<Movie> existingMovie = this.movieRepository.findById(id);
        if(existingMovie.isPresent()){
            List<Rating> ratings  = existingMovie.get().getRating();
            Integer avg = (ratings.stream().map(e-> e.getRating()).mapToInt(Integer::intValue).sum())/ratings.size();
            RatingDto avgDto = new RatingDto();
            avgDto.setAvgRating(avg);
            existingMovie.get().setAvgRating(avgDto);
        }else{
            throw new NoSuchElementException("Movie Does not Exist");
        }
        return existingMovie.get();
    }
}
