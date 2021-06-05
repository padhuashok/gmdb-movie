package com.galvanize.gmdbmovie.repository;

import com.galvanize.gmdbmovie.domain.Rating;
import com.galvanize.gmdbmovie.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface RatingRepository extends CrudRepository<Rating, Long> {
}
