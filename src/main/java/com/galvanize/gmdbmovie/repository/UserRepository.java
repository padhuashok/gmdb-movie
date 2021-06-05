package com.galvanize.gmdbmovie.repository;

import com.galvanize.gmdbmovie.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Long> {
}
