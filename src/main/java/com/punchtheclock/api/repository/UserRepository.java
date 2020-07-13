package com.punchtheclock.api.repository;

import com.punchtheclock.api.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
}
