package com.galvanize.gmdbmovie.repository;

import com.galvanize.gmdbmovie.domain.Actor;
import org.springframework.data.repository.CrudRepository;

public interface ActorRepository extends CrudRepository<Actor,Long> {
}
