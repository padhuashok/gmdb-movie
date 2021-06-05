package com.galvanize.gmdbmovie.repository;

import com.galvanize.gmdbmovie.domain.Movie;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MovieRepository extends CrudRepository<Movie,Long> {

    Optional<Movie> findByTitle(String title);
}
