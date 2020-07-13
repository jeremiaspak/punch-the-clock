package com.punchtheclock.api.repository;

import com.punchtheclock.api.model.TimeRegistry;
import org.springframework.data.repository.CrudRepository;

public interface TimeRegistryRepository extends CrudRepository<TimeRegistry, Integer> {
  Iterable<TimeRegistry> findByUserId(Integer userId);
}
