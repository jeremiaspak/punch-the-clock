package com.punchtheclock.api.service;

import com.punchtheclock.api.model.TimeRegistry;
import com.punchtheclock.api.repository.TimeRegistryRepository;
import com.punchtheclock.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class TimeRegistryService {

  @Autowired
  private UserService userService;

  @Autowired
  private TimeRegistryRepository timeRegistryRepository;

  public Iterable<TimeRegistry> findByUserId(Integer userId) {
    return timeRegistryRepository.findByUserId(userId);
  }

  public Optional<TimeRegistry> create(Integer userId, TimeRegistry registry) {
    return userService.get(userId).map(user -> {
      registry.setUser(user);
      return timeRegistryRepository.save(registry);
    });
  }

  public void delete(Integer id) {
    timeRegistryRepository.deleteById(id);
  }
}
