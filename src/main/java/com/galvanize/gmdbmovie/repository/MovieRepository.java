package com.galvanize.gmdbmovie.repository;

import com.galvanize.gmdbmovie.domain.Movie;
import org.springframework.data.repository.CrudRepository;

public interface MovieRepository extends CrudRepository<Movie,Long> {

}
