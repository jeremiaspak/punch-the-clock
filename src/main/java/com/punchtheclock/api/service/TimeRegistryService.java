package com.punchtheclock.api.service;

import com.punchtheclock.api.model.TimeRegistry;
import com.punchtheclock.api.repository.TimeRegistryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Iterator;
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

  public String getTotalHours(Iterable<TimeRegistry> registries) {
    Iterator<TimeRegistry> iterator = registries.iterator();
    LocalDateTime lastIn = null;
    Duration total = Duration.ZERO;

    while (iterator.hasNext()) {
      TimeRegistry registry = iterator.next();
      if (registry.getType() == TimeRegistry.TYPE.IN) {
        lastIn = registry.getCreatedAt();
      } else {
        if (lastIn != null) {
          total = total.plus(
            Duration.between(lastIn, registry.getCreatedAt())
          );
          lastIn = null;
        }
      }
    }

    return String.format(
      "%02d:%02d:%02d",
      total.toHoursPart(),
      total.toMinutesPart(),
      total.toSecondsPart()
    );
  }
}
